package com.wguproject.videogamebacklog.ui.screens.search

import android.util.Log
import androidx.collection.emptyIntList
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aws.smithy.kotlin.runtime.http.auth.AnonymousIdentity.attributes
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.User
import com.wguproject.videogamebacklog.Graph
import com.wguproject.videogamebacklog.data.Game
import com.wguproject.videogamebacklog.data.GameRepository
import com.wguproject.videogamebacklog.utils.ImageSize
import com.wguproject.videogamebacklog.utils.ImageType
import com.wguproject.videogamebacklog.utils.imageBuilder
import com.wguproject.videogamebacklog.vgbService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class SearchViewModel(
    private val gameRepository: GameRepository = Graph.gameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _currentUserId = mutableStateOf("")
    val currentUserId: State<String> = _currentUserId

    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Amplify.Auth.getCurrentUser(
                    {
                        Log.i("Get User Initialization", it.userId)
                        _currentUserId.value = it.userId

                        Log.i("Getting User from aws ", _currentUserId.value)
                        loadUser(currentUserId.value)
                    },
                    {Log.e("Get Current User","Unable to fetch current user",it.cause)}
                )


            } catch (e: Exception) {
                // Handle the case where there's no authenticated user
                _currentUserId.value = ""
            }
        }
    }
    fun loadUser(userId: String) {
        viewModelScope.launch {
            _currentUser.value =
                gameRepository.findAWSUser(userId) ?: gameRepository.createAWSDBUser(userId)
        }
    }

    fun onSearchTitleChanged(newString: String) {
        _uiState.value = _uiState.value.copy(searchTitle = newString)
    }
    fun loadGameDetails(game: Game?){
            _uiState.update { currentState ->
                currentState.copy(selectedGame = game)
        }
    }
    fun clearSearch(){
        _uiState.value = _uiState.value.copy(
        searchTitle = ""
        )
    }

    private val viewModelJob = SupervisorJob()
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("SearchViewModel", "Coroutine exception: ${exception.message}")
        _uiState.value = _uiState.value.copy(
            loading = false,
            error = "An error occurred: ${exception.message}"
        )
    }

    private fun buildQuery(title: String): String {
        return "fields id,aggregated_rating,category,first_release_date,genres,name,similar_games,summary;" +
                "limit 15; " +
                "search \"$title\";"

    }

    fun findGameById(id: Int): Game?{
        Log.i("Search Detail Find Game",_uiState.value.list.toString())
        return _uiState.value.list.find { it.id == id }
    }


    fun searchForGame(title: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(loading = true)
                val query = buildQuery(title)
                val requestBody = query.toRequestBody("text/plain".toMediaType())
                val response = vgbService.getGames(requestBody)
                val filteredResponse = response.filter { it.aggregated_rating != null }
                _uiState.value = _uiState.value.copy(
                    list = filteredResponse,
                    error = null
                )
                fetchCoverArtForGames()
                Log.i("Search", response[0].name)
            } catch (e: Exception) {
                Log.e("API Error", "Error: ${e.message}", e)
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    error = "Error fetching games. ${e.message}",
                    list = emptyList()

                )
            }
        }
        Log.d("SearchViewModel", "searchForGame method completed")
    }
    fun addGame(game: Game): Result<Unit> {
        return try {
            viewModelScope.launch {
                val newGame = com.amplifyframework.datastore.generated.model.Game.builder()
                    .aggregatedRating(game.aggregated_rating)
                    .name(game.name)
                    .genres(game.genres)
                    .similarGames(game.similar_games)
                    .category(game.category)
                    .complete(game.completed)
                    .coverUrl(game.coverUrl)
                    .summary(game.summary)
                    .firstReleaseDate(Temporal.Date(Date(game.first_release_date?:0L)))
                    .user(currentUser.value)
                    .id(game.id.toString())
                    .build()

                Amplify.API.mutate(
                    ModelMutation.create(newGame),
                    { Log.i("MyAmplifyApp", "Added Game with id: ${it.data.id}")},
                    { Log.e("MyAmplifyApp", "Create failed", it)},
                )
                gameRepository.addGame(game = game)

            }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AddGame", "Error adding game: ${e.message}")
            Result.failure(e)
        }

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
            val updatedList = _uiState.value.list.map { game ->
                try {
                    val coverid = fetchCoverArt(game.id)
                    Log.i("Search", "Coverid = ${coverid}")
                    val coverUrl = imageBuilder(coverid ?: "", ImageSize.COVER_BIG, ImageType.JPEG)
                    Log.i("Search", "Coverid = ${coverUrl}")
                    game.copy(coverUrl = coverUrl)
                } catch (e: Exception) {
                    Log.e(
                        "CoverArt",
                        "Error fetching cover art for game ${game.name}: ${e.message}"
                    )
                    game
                }
            }
            _uiState.value = _uiState.value.copy(list = updatedList, loading = false, error = null)
        }
    }
    fun convertUnixTimestamp(timestamp: Long): String {
        return if(timestamp != null && timestamp != 0L) {
            val date = Date(timestamp * 1000L) // Convert seconds to milliseconds
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(date)
        }else {
            "Release date unknown"
        }
    }

}


data class SearchUiState(
    val loading: Boolean = false,
    val list: List<Game> = emptyList(),
    val error: String? = null,
    val searchTitle: String = "",
    val selectedGame: Game? = null
)
