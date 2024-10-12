package com.wguproject.videogamebacklog.ui.screens.login

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.amplifyframework.core.Amplify
import com.amplifyframework.ui.authenticator.ui.Authenticator
import com.wguproject.videogamebacklog.GameViewModel
import com.wguproject.videogamebacklog.ui.screens.AppBarBottom

@Composable
fun LogoutView(
    navController: NavController,
    gameViewModel: GameViewModel
) {
    Scaffold(bottomBar = { AppBarBottom(navController) }) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
            Authenticator(Modifier.padding(it)) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(onClick = { Amplify.Auth.signOut {} }) {
                        Text(text = "Sign Out")
                    }
                }
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                ReportGenerationButton(viewModel = gameViewModel)
            }

        }
    }
}

@Composable
fun ReportGenerationButton(viewModel: GameViewModel) {
    val context = LocalContext.current
    val reportState by viewModel.reportState.collectAsState()
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Button(onClick = { viewModel.generateReport(context) }) {
            Text("Generate Report")
        }

        reportState?.let { state ->
            if (state.startsWith("Error")) {
                Text(state, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
            } else {
                Text("Report generated: $state",textAlign = TextAlign.Center, color = Color.Black)
            }
        }
    }
}