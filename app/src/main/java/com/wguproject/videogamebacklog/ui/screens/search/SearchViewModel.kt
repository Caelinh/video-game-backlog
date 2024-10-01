package com.wguproject.videogamebacklog.ui.screens.search

import android.app.GameState
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.igdb.apicalypse.APICalypse
import com.wguproject.videogamebacklog.data.APIGameItem
import com.wguproject.videogamebacklog.utils.ImageSize
import com.wguproject.videogamebacklog.utils.ImageType
import com.wguproject.videogamebacklog.utils.imageBuilder
import com.wguproject.videogamebacklog.vgbService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


class SearchViewModel() : ViewModel() {

    private val _gameListState = mutableStateOf(resultsState())
    val gameListState: State<resultsState> = _gameListState

    var searchTitleState by mutableStateOf("")

    fun onSearchTitleChanged(newString: String) {
        searchTitleState = newString
    }

    private val viewModelJob = SupervisorJob()
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("SearchViewModel", "Coroutine exception: ${exception.message}")
        _gameListState.value = _gameListState.value.copy(
            loading = false,
            error = "An error occurred: ${exception.message}"
        )
    }

    private fun buildQuery(title: String): String {
        return "fields id,aggregated_rating,category,first_release_date,genres,name,similar_games,summary;" +
                "limit 15; " +
                "search \"$title\"; "
    }


    fun searchForGame(title: String) {
        val query = buildQuery(title)
        Log.d("API Request", "Query: ${query}")
        viewModelScope.launch {
            try {
                _gameListState.value = _gameListState.value.copy(
                    loading = true
                )
                val requestBody = query.toRequestBody("text/plain".toMediaType())
                val response = vgbService.getGames(requestBody)
                _gameListState.value = _gameListState.value.copy(
                    list = response
                )
                fetchCoverArtForGames()

                Log.i("Search", response[0].name)

            } catch (e: Exception) {
                Log.e("API Error", "Error: ${e.message}", e)
                _gameListState.value = _gameListState.value.copy(
                    loading = false,
                    error = "Error fetching games. ${e.message}",
                    list = emptyList()

                )
            }
        }
        Log.d("SearchViewModel", "searchForGame method completed")
    }

    private suspend fun fetchCoverArt(gameId: Int): String? {
        return withContext(viewModelJob + errorHandler) {
            try {
                val query = "fields image_id; where game = $gameId;"
                val requestBody = query.toRequestBody("text/plain".toMediaType())
                val coverResponse = vgbService.getCovers(requestBody)
                coverResponse.firstOrNull()?.image_id
            } catch (e: Exception) {
                Log.e("CoverArt", "Error fetching cover for game $gameId: ${e.message}${e.cause}")
                null
            }
        }
    }

    private fun fetchCoverArtForGames() {
        viewModelScope.launch(viewModelJob + errorHandler) {
            val currentList = _gameListState.value.list
            val updatedList = currentList.mapNotNull { game ->
                try {
                    val coverid = fetchCoverArt(game.id)
                    Log.i("Search", "Coverid = ${coverid}")
                    val coverUrl =
                        imageBuilder(coverid ?: "", ImageSize.COVER_SMALL, ImageType.JPEG)
                    Log.i("Search", "Coverid = ${coverUrl}")
                    game.copy(coverUrl = coverUrl)
                } catch (e: Exception) {
                    Log.e(
                        "CoverArt",
                        "Error fetching cover art for game ${game.name}: ${e.message}"
                    )
                    null
                }
            }
            _gameListState.value = _gameListState.value.copy(list = updatedList, loading = false)
        }
    }
}


data class resultsState(
    val loading: Boolean = false,
    val list: List<APIGameItem> = emptyList(),
    val error: String? = null
)
