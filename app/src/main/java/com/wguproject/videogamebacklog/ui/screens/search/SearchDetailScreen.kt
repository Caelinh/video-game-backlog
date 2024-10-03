package com.wguproject.videogamebacklog.ui.screens.search

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.wguproject.videogamebacklog.Screen
import com.wguproject.videogamebacklog.data.Game
import com.wguproject.videogamebacklog.ui.screens.AppBarView
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import java.util.Date

@Composable
fun SearchDetailScreen(
    id: Int,
    viewModel: SearchViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(id){
        if (id != 0){
            viewModel.loadGameDetails(id)
        }
    }
    Scaffold(
        topBar = { AppBarView(title = "Video GameBacklog") }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {
            uiState.selectedGame?.let { game ->


                Image(
                    painter = rememberAsyncImagePainter(game.coverUrl),
                    contentDescription = "Cover art for a video game.",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(240.dp)
                )
                Text(
                    text = game.name,
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Summary: ${game.summary}",
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.Normal),
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxHeight(.5f)
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState())
                )
                Text(
                    text = "Release Date: ${viewModel.convertUnixTimestamp(game.first_release_date?:0L)}",
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Rating: ${game.aggregated_rating?.roundToInt()}",
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            val result = viewModel.addGame(game)
                            Log.i("Saving the game", game.toString())
                            if (result.isSuccess){
                                viewModel.clearSearchResults()
                                navController.navigate(Screen.HomeScreen.route)
                            }
                        }

                    }) {
                    Text(text = "Save", color = Color.White)
                }
            }
        }
    }
}