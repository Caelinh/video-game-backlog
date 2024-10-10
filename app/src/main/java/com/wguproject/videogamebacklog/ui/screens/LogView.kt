package com.wguproject.videogamebacklog.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.wguproject.videogamebacklog.data.Game
import androidx.compose.material.Card
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.compose.material.DismissDirection
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.wguproject.videogamebacklog.ui.screens.AppBarView
import com.wguproject.videogamebacklog.GameViewModel
import com.wguproject.videogamebacklog.R
import com.wguproject.videogamebacklog.Screen
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LogView(
    navController: NavController,
    viewModel: GameViewModel
) {
    Scaffold(
        bottomBar = { AppBarBottom(navController) }
    ) {
        val gameList = viewModel.getAllBackLogGames.collectAsState(initial = listOf())
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "BackLog",
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
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart) {
                                viewModel.deleteGame(game)
                            } else if (it == DismissValue.DismissedToEnd) {
                                val completedGame = game.copy(
                                    completed = true
                                )
                                Log.i("SwipeLog", completedGame.toString())
                                viewModel.updateGame(completedGame)
                            }
                            true
                        }
                    )
                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            val color by animateColorAsState(
                                if (dismissState.dismissDirection == DismissDirection.EndToStart) Color.Red
                                else if (dismissState.dismissDirection == DismissDirection.StartToEnd) Color.Cyan
                                else Color.Transparent, label = ""
                            )
                            val alignment = Alignment.CenterEnd
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = Alignment.CenterEnd

                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete Icon",
                                    tint = Color.White
                                )
                            }
                        },
                        directions = setOf(
                            DismissDirection.EndToStart,
                            DismissDirection.StartToEnd
                        ),
                        dismissThresholds = { FractionalThreshold(0.50F) },
                        dismissContent = {
                            VideoGameItem(game = game) {
                                val id = game.id
                                viewModel.selectedGame = game
                                navController.currentBackStackEntry?.savedStateHandle?.set("game", game)
                                navController.currentBackStackEntry?.savedStateHandle?.set("showSaveButton", false)
                                navController.currentBackStackEntry?.savedStateHandle?.set("showEditButton", true)
                                navController.navigate(Screen.SearchDetailScreen.route + "/$id")
                            }
                        })
                }
            }
        }
    }
}

@Composable
fun VideoGameItem(game: Game, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(.95F)
            .clickable {
                onClick()
            },
        elevation = 10.dp,
        backgroundColor = Color.White,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(game.coverUrl),
                contentDescription = "Cover art for a video game.",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)

            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = game.name, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                game.aggregated_rating?.let { rating ->
                    Row {
                        Text(
                            text = "${rating.roundToInt()/10}/10",
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                        Icon(Icons.Filled.Star,null, tint = colorResource(id = R.color.star_yellow))
                    }
                }
            }

        }
    }

}