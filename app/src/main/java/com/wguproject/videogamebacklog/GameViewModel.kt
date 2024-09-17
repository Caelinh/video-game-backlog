package com.wguproject.videogamebacklog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel() {

    var gameTitleState by mutableStateOf("")
    var gameDescriptionState by mutableStateOf("")

    fun onGameTitleChanged(newString:String){
        gameTitleState = newString
    }

    fun onGameDescriptionChanged(newString:String){
        gameDescriptionState = newString
    }

}