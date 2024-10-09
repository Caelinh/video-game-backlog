package com.wguproject.videogamebacklog

import android.util.Log
import androidx.collection.emptyIntList
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
import com.amplifyframework.datastore.generated.model.BackLog
import com.wguproject.videogamebacklog.data.Game
import com.wguproject.videogamebacklog.data.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GameViewModel(
    private val gameRepository: GameRepository = Graph.gameRepository
) : ViewModel() {

    var selectedGame by mutableStateOf(Game(id = 0, category = 0, aggregated_rating = 0.0, first_release_date = 0L, genres = emptyList(), name = ""))

    var gameTitleState by mutableStateOf("")
    var gameDescriptionState by mutableStateOf("")

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

    lateinit var getAllBackLogGames: Flow<List<Game>>

    init {
        viewModelScope.launch() {
          getAllBackLogGames = gameRepository.getAllBacklogGames()


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
    }

    fun deleteGame(game: Game) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.deleteGame(game = game)
        }
    }

}