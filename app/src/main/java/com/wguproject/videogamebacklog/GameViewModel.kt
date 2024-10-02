package com.wguproject.videogamebacklog

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
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

    var gameTitleState by mutableStateOf("")
    var gameDescriptionState by mutableStateOf("")

    fun onGameTitleChanged(newString: String) {
        gameTitleState = newString
    }

    fun onGameDescriptionChanged(newString: String) {
        gameDescriptionState = newString
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
    fun getBacklog(id: String) {
        Amplify.API.query(ModelQuery.get(BackLog::class.java, id),
            { Log.i("MyAmplifyApp", "Query results = ${(it.data as BackLog).id}") },
            { Log.e("MyAmplifyApp", "Query failed", it) }
        );
    }

}