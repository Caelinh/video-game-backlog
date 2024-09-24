package com.wguproject.videogamebacklog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wguproject.videogamebacklog.ui.screens.AddEditDetailView
import com.wguproject.videogamebacklog.GameViewModel
import com.wguproject.videogamebacklog.Screen
import com.wguproject.videogamebacklog.ui.screens.LogView
import com.wguproject.videogamebacklog.ui.screens.login.LoginView
import com.wguproject.videogamebacklog.ui.screens.login.PostLoginView

@Composable
fun Navigation(
    viewModel: GameViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.LoginScreen.route){
            LoginView(navController = navController)
        }
        composable(
            Screen.PostLoginScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = false
                }
                )){
            val id = if (it.arguments != null) it.arguments!!.getString("id") else "12"
            if (id != null) {
                PostLoginView(userId = id,navController)
            }
            }

        composable(Screen.HomeScreen.route) {
            LogView(navController, viewModel)
        }
        composable(
            Screen.AddScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ) {
            val id = if (it.arguments != null) it.arguments!!.getLong("id") else 0L
            AddEditDetailView(id = id, viewModel = viewModel, navController = navController)
        }

    }
}