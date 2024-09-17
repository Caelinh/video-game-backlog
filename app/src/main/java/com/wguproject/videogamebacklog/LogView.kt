package com.wguproject.videogamebacklog

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.wguproject.videogamebacklog.data.DummyGame

@Composable
fun LogView(
    navController: NavController,
    viewModel: GameViewModel
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {AppBarView(title = "Video Game Backlog", {
            Toast.makeText(context, "Button Clicked", Toast.LENGTH_LONG).show()
        })},
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.White,
                containerColor = Color.Black,
                onClick = {
                    Toast.makeText(context, "FAB Button Clicked", Toast.LENGTH_LONG).show()
                    navController.navigate(Screen.AddScreen.route)
                })
            {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
}
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)){
            items(DummyGame.backLog){
                game -> VideoGameItem(game = game) {
            }
            }
        }
    }
}

@Composable
fun VideoGameItem(game: Game, onClick: () -> Unit){
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