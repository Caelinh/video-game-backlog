package com.wguproject.videogamebacklog.ui.screens.recommendations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.wguproject.videogamebacklog.Graph
import com.wguproject.videogamebacklog.data.Game
import com.wguproject.videogamebacklog.data.GameRepository
import com.wguproject.videogamebacklog.utils.ImageSize
import com.wguproject.videogamebacklog.utils.ImageType
import com.wguproject.videogamebacklog.utils.imageBuilder
import com.wguproject.videogamebacklog.vgbService
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class RecommendedGamesViewModel(private val gameRepository: GameRepository = Graph.gameRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow<RandomGamesUiState>(RandomGamesUiState.Loading)
    val uiState: StateFlow<RandomGamesUiState> = _uiState.asStateFlow()

    var query: String = ""

    init {
        viewModelScope.launch {
        loadRandomRelatedGames()
        }

    }

    private fun loadRandomRelatedGames() {
        viewModelScope.launch {
            try {
                val backlogGames = gameRepository.getAllComparisonGames().first()
                val recommendedGameIds = backlogGames.flatMap { it.similar_games ?: emptyList() }
                val randomizeGameList = recommendedGameIds.asSequence().shuffled().take(10).toList()
                val query = buildQuery(randomizeGameList)

                Log.i("Recommended games ids", query)

                val games = searchForGame(query)
                _uiState.value = RandomGamesUiState.Success(games)

                fetchCoverArtForGames(games)
            } catch (e: Exception) {
                _uiState.value = RandomGamesUiState.Error("Failed to load games: ${e.message}")
            }
        }
    }

    private fun buildQuery(gameIds: List<Int>): String {
        val idsString = gameIds.joinToString(", ")
        return "where id = ($idsString);" +
                "fields id,aggregated_rating,category,first_release_date,genres,name,similar_games,summary;"
    }

    private suspend fun searchForGame(query: String): List<Game> {
        return try {
            val requestBody = query.toRequestBody("text/plain".toMediaType())
            val response = vgbService.getGames(requestBody)
            Log.i("Search", "First game name: ${response.firstOrNull()?.name ?: "N/A"}")
            response
        } catch (e: Exception) {
            Log.e("API Error", "Error: ${e.message}", e)
            emptyList()
        }
    }

    private suspend fun fetchCoverArt(gameId: Int): String? {
        return withContext(viewModelScope.coroutineContext) {
            try {
                val query = "fields image_id; where game = $gameId;"
                val requestBody = query.toRequestBody("text/plain".toMediaType())
                val coverResponse = vgbService.getCovers(requestBody)
                coverResponse.firstOrNull()?.image_id
            } catch (e: Exception) {
                Log.e("CoverArt", "Error fetching cover for game $gameId: ${e.message}", e)
                null
            }
        }
    }

    private fun fetchCoverArtForGames(games: List<Game>) {
        viewModelScope.launch {
            _uiState.value = RandomGamesUiState.Loading
            val updatedGames = games.map { game ->
                try {
                    val coverId = fetchCoverArt(game.id)
                    val coverUrl = imageBuilder(coverId ?: "", ImageSize.COVER_BIG, ImageType.JPEG)
                    Log.i("Search", "Cover URL for ${game.name}: $coverUrl")
                    game.copy(coverUrl = coverUrl)
                } catch (e: Exception) {
                    Log.e("CoverArt", "Error fetching cover art for game ${game.name}: ${e.message}")
                    game
                }
            }
            _uiState.value = RandomGamesUiState.Success(updatedGames)
        }
    }

}

sealed class RandomGamesUiState {
    object Loading : RandomGamesUiState()
    data class Success(val games: List<Game>) : RandomGamesUiState()
    data class Error(val message: String) : RandomGamesUiState()
}

