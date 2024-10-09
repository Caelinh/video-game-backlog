package com.wguproject.videogamebacklog.ui.screens.search

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.wguproject.videogamebacklog.R
import com.wguproject.videogamebacklog.Screen
import com.wguproject.videogamebacklog.data.Game
import com.wguproject.videogamebacklog.ui.screens.AppBarBottom
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
    val game = navController.previousBackStackEntry?.savedStateHandle?.get<Game>("game")
    val showSaveButton = navController.previousBackStackEntry?.savedStateHandle?.get<Boolean>("showSaveButton") ?: true

    LaunchedEffect(game) {
        viewModel.loadGameDetails(game)

    }
    Scaffold(
        bottomBar = { AppBarBottom(navController) }
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
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(220.dp,300.dp)
                            .shadow(elevation = 8.dp,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp),
                            spotColor = Color.Black.copy(alpha = 0.50f))

                    )

                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = game.name,
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = game.summary ?: "No summary available",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxHeight(.5f)
                            .padding(8.dp)
                            .verticalScroll(rememberScrollState())
                    )
                    Text(
                        text = "Release Date: ${viewModel.convertUnixTimestamp(game.first_release_date ?: 0L)}",
                        color = Color.Black,
                        style = TextStyle(fontWeight = FontWeight.ExtraBold),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    game.aggregated_rating?.let { rating ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = "${rating.roundToInt()/10}/10",
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            Icon(Icons.Filled.Star,null, tint = colorResource(id = R.color.star_yellow))
                        }

                    }

                }
                if (showSaveButton) {
                    Button(
                        onClick = {
                            viewModel.viewModelScope.launch {
                                val result = viewModel.addGame(game)
                                Log.i("Saving the game", game.toString())
                                if (result.isSuccess) {
                                    navController.navigate(Screen.HomeScreen.route) {
                                        popUpTo(Screen.SearchDetailScreen.route) {
                                            inclusive = true
                                        }
                                    }

                                }
                            }

                        }) {
                        Text(text = "Save", color = Color.White)
                    }
                }
            }
        }
    }
}
