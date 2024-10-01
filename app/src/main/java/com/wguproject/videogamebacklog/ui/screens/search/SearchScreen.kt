package com.wguproject.videogamebacklog.ui.screens.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.amplifyframework.api.ApiException
import com.wguproject.videogamebacklog.R
import com.wguproject.videogamebacklog.data.APIGameItem
import com.wguproject.videogamebacklog.data.Game
import com.wguproject.videogamebacklog.ui.screens.AppBarView

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val viewState by viewModel.gameListState

    Scaffold(
        topBar = {
            AppBarView(title = "Video Game Backlog")
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
                label = "Title",
                value = viewModel.searchTitleState,
                onValueChanged = { viewModel.onSearchTitleChanged(it) })
            Button(onClick = { viewModel.searchForGame(viewModel.searchTitleState) }) {
                Text(text = "Search")
            }
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    viewState.loading -> {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }

                    viewState.error != null -> {
                        Text("Error occurred during search")
                    }

                    else -> {
                        SearchResults(results = viewState.list)
                    }
                }
            }

        }

    }
}

@Composable
fun SearchTextField(
    label: String, value: String, onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            cursorColor = colorResource(id = R.color.black),
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black),
        )
    )
}

@Composable
fun SearchResults(results: List<APIGameItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(results) { game ->
            GameItem(game = game)
        }
    }
}

@Composable
fun GameItem(game: APIGameItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
            .height(100.dp)
            .clickable {},

        elevation = 10.dp,
        backgroundColor = Color.White
    ) {
        Row(
            Modifier
                .wrapContentSize()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(game.coverUrl),
                contentDescription = "Cover art for a video game.",
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
            )

            Column(Modifier.fillMaxWidth()
                    .padding(8.dp)) {

                Text(
                    text = game.name,
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.ExtraBold)
                )
                Text(
                    text = "Release Date: ${game.first_release_date.toString()}",
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.ExtraBold)
                )
                Text(
                    text = "Rating: ${game.aggregated_rating}",
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.ExtraBold)
                )
            }
        }

    }
}


