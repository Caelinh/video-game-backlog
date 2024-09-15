package com.wguproject.videogamebacklog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LogView() {

    Scaffold(
        topBar = {AppBarView(title = "Video Game Backlog")}
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(it)){}
    }
}