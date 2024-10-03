package com.wguproject.videogamebacklog.ui.screens.CompletedLog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wguproject.videogamebacklog.Graph
import com.wguproject.videogamebacklog.data.Game
import com.wguproject.videogamebacklog.data.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CompletedLogViewModel(
    private val gameRepository: GameRepository = Graph.gameRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(CompletedUiState())
    val uiState: StateFlow<CompletedUiState> = _uiState.asStateFlow()

    lateinit var allCompletedGames: Flow<List<Game>>

    init {
        viewModelScope.launch() {
            allCompletedGames = gameRepository.getAllCompletedGames()
            _uiState.update { currentState ->
                currentState.copy(list = allCompletedGames)
            }

        }
    }

}
data class CompletedUiState(
    val loading: Boolean = false,
    val list: Flow<List<Game>> = emptyFlow(),
    val error: String? = null,
    val selectedGame: Game? = null

)