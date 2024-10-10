package com.wguproject.videogamebacklog

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
import kotlinx.coroutines.launch
import java.util.Date

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

    fun addGame(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.addGame(game = game)
        }

    }
    fun getGameById(id: Int): Flow<Game> {
        return gameRepository.getGameById(id)
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

}