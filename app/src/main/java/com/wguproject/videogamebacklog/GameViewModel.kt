package com.wguproject.videogamebacklog

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.wguproject.videogamebacklog.data.Game
import com.wguproject.videogamebacklog.data.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GameViewModel(
    private val gameRepository: GameRepository = Graph.gameRepository
) : ViewModel() {

    var gameTitleState by mutableStateOf("")
    var gameDescriptionState by mutableStateOf("")

    fun onGameTitleChanged(newString: String) {
        gameTitleState = newString
    }

    fun onGameDescriptionChanged(newString: String) {
        gameDescriptionState = newString
    }

    lateinit var getAllGames: Flow<List<Game>>

    init {
        viewModelScope.launch {
            getAllGames = gameRepository.getAllGames()
        }
    }

    fun addGame(game: Game) {
//        viewModelScope.launch(Dispatchers.IO) {
//            gameRepository.addGame(game = game)
//
//        }
        viewModelScope.launch(Dispatchers.IO) {
            Amplify.Auth.getCurrentUser(
                {
                    val userId = it.userId
                },
                {
                    Log.e("MyAmplifyApp","Error saving user $it")
                }
            )
            val newGame = com.amplifyframework.datastore.generated.model.Game.builder()
                .name(game.title)
                .description(game.description)
                .build()
            Amplify.API.mutate(
                ModelMutation.create(newGame),
                { Log.i("My Amplify Backend", "Added Todo with id: ${it.data.id}") },
                { Log.e("MyAmplify Backend", "Create failed", it) }

            )

        }

    }

    fun getGameById(id: Long): Flow<Game> {
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