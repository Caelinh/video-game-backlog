package com.wguproject.videogamebacklog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wguproject.videogamebacklog.data.Game
import com.wguproject.videogamebacklog.data.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GameViewModel(
    private val gameRepository: GameRepository = Graph.gameRepository
):ViewModel() {

    var gameTitleState by mutableStateOf("")
    var gameDescriptionState by mutableStateOf("")

    fun onGameTitleChanged(newString:String){
        gameTitleState = newString
    }

    fun onGameDescriptionChanged(newString:String){
        gameDescriptionState = newString
    }

    lateinit var getAllGames: Flow<List<Game>>

    init {
        viewModelScope.launch {
            getAllGames = gameRepository.getAllGames()
        }
    }

    fun addGame(game: Game){
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.addGame(game = game)
        }
    }

    fun getGameById(id:Long):Flow<Game> {
        return gameRepository.getGameById(id)
    }

    fun updateGame(game: Game){
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.updateGame(game = game)
        }
    }

    fun deleteGame(game: Game){
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.deleteGame(game = game)
        }
    }

}