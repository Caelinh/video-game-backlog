package com.wguproject.videogamebacklog

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.amplifyframework.core.Amplify
import com.amplifyframework.ui.authenticator.ui.Authenticator

@Composable
fun LoginView( navController: NavController){
    val context = LocalContext.current
    Authenticator { state ->
        navController.navigate(Screen.HomeScreen.route)
        Column {
            Text(
                text = "Hello ${state.user}",
            )
            Button(onClick = {
                Amplify.Auth.signOut {  }
            }) {
                Text(text = "Sign Out")
            }
        }
    }
}