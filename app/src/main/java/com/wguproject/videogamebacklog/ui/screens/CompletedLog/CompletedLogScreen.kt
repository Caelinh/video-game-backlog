package com.wguproject.videogamebacklog.ui.screens.CompletedLog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wguproject.videogamebacklog.ui.screens.AppBarBottom
import com.wguproject.videogamebacklog.ui.screens.VideoGameItem

@Composable
fun CompletedLogScreen(navController: NavController, viewModel: CompletedLogViewModel) {
    val viewState by viewModel.uiState.collectAsState()
    Scaffold(
        bottomBar = { AppBarBottom(navController) }
    ) {
        val gameList = viewModel.allCompletedGames.collectAsState(initial = listOf())
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Completed Games",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(gameList.value, key = { game -> game.id }) { game ->
                    VideoGameItem(game = game, onClick = {})
                }
            }
        }
    }
}
