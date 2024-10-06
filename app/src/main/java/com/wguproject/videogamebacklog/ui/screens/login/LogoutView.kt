package com.wguproject.videogamebacklog.ui.screens.login

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.amplifyframework.core.Amplify
import com.amplifyframework.ui.authenticator.ui.Authenticator
import com.wguproject.videogamebacklog.ui.screens.AppBarBottom

@Composable
fun LogoutView(
    navController: NavController
){
    Scaffold(bottomBar = { AppBarBottom(navController)}) {
        Authenticator(Modifier.padding(it)) {
            Button(onClick = { Amplify.Auth.signOut{} }) {
                Text(text = "Sign Out")

            }
        }

    }
}