package com.wguproject.videogamebacklog.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.wguproject.videogamebacklog.ui.screens.AppBarView
import com.wguproject.videogamebacklog.GameViewModel
import com.wguproject.videogamebacklog.Screen
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LogView(
    navController: NavController,
    viewModel: GameViewModel
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            AppBarView(title = "Video Game Backlog") {
                Toast.makeText(context, "Button Clicked", Toast.LENGTH_LONG).show()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.White,
                containerColor = Color.Black,
                onClick = {
                    navController.navigate(Screen.SearchScreen.route)
                })
            {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        val gameList = viewModel.getAllBackLogGames.collectAsState(initial = listOf())

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(gameList.value, key = {game -> game.id  }) { game ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            viewModel.deleteGame(game)
                        } else if (it == DismissValue.DismissedToEnd){
                            val completedGame = game.copy(
                                completed = true
                            )
                            Log.i("SwipeLog",completedGame.toString())
                            viewModel.updateGame(completedGame)
                        }

                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    background ={
                                val color by animateColorAsState(
                                    if (dismissState.dismissDirection == DismissDirection.EndToStart) Color.Red
                                    else if ( dismissState.dismissDirection == DismissDirection.StartToEnd) Color.Cyan
                                    else Color.Transparent
                                , label = ""
                                )
                        val alignment = Alignment.CenterEnd
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ){
                            Icon(Icons.Default.Delete, contentDescription = "Delete Icon", tint = Color.White)
                        }
                    },
                    directions = setOf(DismissDirection.EndToStart,DismissDirection.StartToEnd),
                    dismissThresholds = {FractionalThreshold(0.25F)},
                    dismissContent = {
                        VideoGameItem(game = game) {
                            val id = game.id
                            navController.navigate(Screen.AddScreen.route + "/$id")
                        }
                    })
            }
        }
    }
}

@Composable
fun VideoGameItem(game: Game, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable {
                onClick()
            },
        elevation = 10.dp,
        backgroundColor = Color.White
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
                    Text(
                        text = "Rating: ${rating.roundToInt()}",
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }
            }

        }
    }
}