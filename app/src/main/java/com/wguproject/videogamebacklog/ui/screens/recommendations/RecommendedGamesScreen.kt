package com.wguproject.videogamebacklog.ui.screens.recommendations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.wguproject.videogamebacklog.Screen
import com.wguproject.videogamebacklog.data.Game
import com.wguproject.videogamebacklog.ui.screens.AppBarBottom
import kotlinx.coroutines.flow.Flow

@Composable
fun RecommendedGamesScreen(navController: NavController,viewModel: RecommendedGamesViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        bottomBar = { AppBarBottom(navController = navController) }
    ) {
        Spacer(modifier = Modifier.padding(it))
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Recommended Games",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            when (val state = uiState) {
                is RandomGamesUiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator(Modifier.size(200.dp))
                    }

                }

                is RandomGamesUiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        items(state.games) { game ->
                            GameListItem(
                                game,
                                onClick = { navController.navigate(Screen.SearchDetailScreen.route + "/${game.id}") })
                        }
                    }
                }

                is RandomGamesUiState.Error -> {
                    Text("Error: ${state.message}")
                }
            }
        }
    }
}
@Composable
fun GameListItem(game: Game, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = game.coverUrl,
                contentDescription = "Game cover",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(4.dp)),
                placeholder = null
            )
        }
    }
}