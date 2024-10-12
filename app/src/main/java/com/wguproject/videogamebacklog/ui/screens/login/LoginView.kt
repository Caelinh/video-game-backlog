package com.wguproject.videogamebacklog.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.amplifyframework.ui.authenticator.ui.Authenticator
import com.wguproject.videogamebacklog.Screen

@Composable
fun LoginView( navController: NavController){
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Authenticator() { state ->
            navController.navigate(Screen.HomeScreen.route)
        }
    }

}
