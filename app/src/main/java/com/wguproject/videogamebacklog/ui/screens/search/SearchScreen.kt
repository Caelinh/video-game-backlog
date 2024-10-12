package com.wguproject.videogamebacklog.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.wguproject.videogamebacklog.R
import com.wguproject.videogamebacklog.Screen
import com.wguproject.videogamebacklog.data.Game
import com.wguproject.videogamebacklog.ui.screens.AppBarBottom
import com.wguproject.videogamebacklog.utils.rememberKeyboardController
import kotlin.math.roundToInt

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel
) {
    val viewState by viewModel.uiState.collectAsState()
    val hideKeyboard = rememberKeyboardController()
    var isError by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            AppBarBottom(navController)
        }
    ) {
        Spacer(Modifier.height(10.dp))
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(10.dp))
            SearchTextField(
                label = "Game Title",
                value = viewState.searchTitle,
                onValueChanged = {
                    viewModel.onSearchTitleChanged(it)
                    isError = false
                },
                isError = isError,
                errorMessage = "Please enter a game title"
                )

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                if (viewState.searchTitle.isBlank()) {
                    isError = true
                } else {
                    viewModel.performSearch(viewState.searchTitle.trim())
                    viewModel.clearSearch()
                    hideKeyboard()
                }

            },
                enabled = !isError && viewState.searchTitle.isNotBlank()
                ) {
                Text(text = "Search")
            }
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    viewState.loading -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Searching for games",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            CircularProgressIndicator(
                                modifier = Modifier.offset(y = 6.dp)
                            )
                        }

                    }
                    viewState.error != null -> {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset(y = 8.dp),
                            text = "Game not found. Please try again",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp)
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                        ) {
                            items(viewState.list, key = { item -> item.id }) { game ->
                                GameItem(game = game, date = viewModel.convertUnixTimestamp(game.first_release_date?:0L),
                                    onClick = {
                                        navController.currentBackStackEntry?.savedStateHandle?.set("game", game)
                                        navController.navigate(Screen.SearchDetailScreen.route + "/${game.id}")
                                    })
                            }
                        }
                    }
                }
            }
        }

    }

}


@Composable
fun SearchTextField(
    label: String, value: String, onValueChanged: (String) -> Unit, isError: Boolean, errorMessage: String
) {
    var nameError by remember { mutableStateOf<String?>(null) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier.fillMaxWidth(.9f),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            cursorColor = colorResource(id = R.color.black),
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black),
        ),
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search,null)
        },
        isError = isError
    )
    if (isError) {
        Text(text = errorMessage, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
    }
}

@Composable
fun GameItem(game: Game,date:String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
            .height(100.dp)
            .clickable { onClick() },
        elevation = 10.dp,
        backgroundColor = Color.White
    ) {
        Row(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberAsyncImagePainter(game.coverUrl),
                contentDescription = "Cover art for a video game.",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )
            Column(
                Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = game.name,
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Release Date: $date",
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.Medium)
                )
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




