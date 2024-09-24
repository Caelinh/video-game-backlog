package com.wguproject.videogamebacklog.ui.screens

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.wguproject.videogamebacklog.ui.screens.AppBarView
import com.wguproject.videogamebacklog.GameViewModel
import com.wguproject.videogamebacklog.Screen


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
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
                    Toast.makeText(context, "FAB Button Clicked", Toast.LENGTH_LONG).show()
                    navController.navigate(Screen.AddScreen.route + "/0L")
                })
            {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        val gameList = viewModel.getAllGames.collectAsState(initial = listOf())

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(gameList.value, key = {game -> game.id  }) { game ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                            viewModel.deleteGame(game)
                        }
                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    background ={
                                val color by animateColorAsState(
                                    if (dismissState.dismissDirection == DismissDirection.EndToStart) Color.Red else Color.Transparent
                                , label = ""
                                )
                        val alignment = Alignment.CenterEnd
                        Box(
                            Modifier.fillMaxSize().background(color).padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ){
                            Icon(Icons.Default.Delete, contentDescription = "Delete Icon", tint = Color.White)
                        }
                    },
                    directions = setOf(DismissDirection.EndToStart),
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = game.title, fontWeight = FontWeight.ExtraBold)
            Text(text = game.description)
        }
    }

}