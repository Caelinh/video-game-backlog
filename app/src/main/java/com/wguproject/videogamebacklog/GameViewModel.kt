package com.wguproject.videogamebacklog

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.collection.emptyIntList
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.BackLog
import com.amplifyframework.datastore.generated.model.User
import com.wguproject.videogamebacklog.data.Game
import com.wguproject.videogamebacklog.data.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GameViewModel(
    private val gameRepository: GameRepository = Graph.gameRepository
) : ViewModel() {

    var selectedGame by mutableStateOf(Game(id = 0, category = 0, aggregated_rating = 0.0, first_release_date = 0L, genres = emptyList(), name = ""))
    var gameTitleState by mutableStateOf("")
    var gameDescriptionState by mutableStateOf("")
    private val _currentUserId = mutableStateOf("")
    val currentUserId: State<String> = _currentUserId
    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> = _currentUser
    private val _reportState = MutableStateFlow<String?>(null)
    val reportState: StateFlow<String?> = _reportState.asStateFlow()

    fun onGameTitleChanged(newString: String) {
        gameTitleState = newString
        selectedGame = selectedGame.copy(
            name = gameTitleState
        )
    }

    fun onGameDescriptionChanged(newString: String) {
        gameDescriptionState = newString
        selectedGame = selectedGame.copy(
            summary = gameDescriptionState
        )
    }
    fun loadUser(userId: String) {
        viewModelScope.launch {
            _currentUser.value =
                gameRepository.findAWSUser(userId) ?: gameRepository.createAWSDBUser(userId)
        }
    }

    lateinit var getAllBackLogGames: Flow<List<Game>>

    init {
        viewModelScope.launch() {
          getAllBackLogGames = gameRepository.getAllBacklogGames()
            try{
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
                _currentUserId.value = ""
            }
        }
    }
    fun updateGame(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.updateGame(game = game)
        }
        viewModelScope.launch(Dispatchers.IO) {
            val updatedGame = com.amplifyframework.datastore.generated.model.Game.builder()
                .aggregatedRating(selectedGame.aggregated_rating)
                .name(selectedGame.name)
                .similarGames(selectedGame.similar_games)
                .category(selectedGame.category)
                .complete(selectedGame.completed)
                .coverUrl(selectedGame.coverUrl)
                .genres(selectedGame.genres)
                .summary(selectedGame.summary)
                .firstReleaseDate(Temporal.Date(Date(selectedGame.first_release_date?:0L)))
                .user(currentUser.value)
                .id(selectedGame.id.toString())
                .build()

            Amplify.API.mutate(ModelMutation.update(updatedGame),
                {Log.i("Updating Amplify Model","Updated Game Successfully")},
                {Log.e("Updating Amplify Model","Unable to update amplify game.",it.cause)})
        }
    }
    fun deleteGame(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.deleteGame(game = game)
        }
    }

    fun generateReport(context: Context) {
        viewModelScope.launch {
            try {
                val games = gameRepository.getAllBacklogGames().first()
                val report = buildString {
                    append("Video Game Backlog Report\n")
                    append("Generated on: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())}\n\n")
                    append("ID,Title,Release Date,Rating,Status\n")
                    games.forEach { game ->
                        append("${game.id},")
                        append("\"${game.name}\",")
                        append("${formatDate(game.first_release_date)},")
                        append("${formatRating(game.aggregated_rating)},")
                        append("${if (game.completed) "Completed" else "In Progress"}\n")
                    }
                }

                val fileName = "GameReport_${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())}.csv"
                val documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                val appDir = File(documentsDir, "MyGameBacklogApp")
                if (!appDir.exists()) {
                    appDir.mkdirs()
                }

                val file = File(appDir, fileName)
                file.writeText(report)
                _reportState.value = file.absolutePath
            } catch (e: Exception) {
                _reportState.value = "Error: ${e.message}"
            }
        }
    }
    private fun formatDate(timestamp: Long?): String {
        return if (timestamp != null) {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(timestamp * 1000))
        } else {
            "N/A"
        }
    }

    private fun formatRating(rating: Double?): String {
        return rating?.let { String.format("%.1f", it / 10) } ?: "N/A"
    }

}