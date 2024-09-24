package com.wguproject.videogamebacklog.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.amplifyframework.ui.authenticator.ui.Authenticator
import com.wguproject.videogamebacklog.Screen

@Composable
fun LoginView( navController: NavController){
    val context = LocalContext.current
    Authenticator { state ->
        navController.navigate(Screen.HomeScreen.route)
    }
}
