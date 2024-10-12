package com.wguproject.videogamebacklog.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wguproject.videogamebacklog.R

@Composable
fun AppTitle() {
    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.video_game_backlog_title),
            contentDescription = "Title that says video game backlog",
            modifier = Modifier.fillMaxWidth().height(50.dp), contentScale = ContentScale.FillBounds
        )
    }
}