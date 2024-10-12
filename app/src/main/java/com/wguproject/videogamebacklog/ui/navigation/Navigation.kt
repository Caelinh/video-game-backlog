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
import com.wguproject.videogamebacklog.ui.screens.CompletedLog.CompletedLogScreen
import com.wguproject.videogamebacklog.ui.screens.CompletedLog.CompletedLogViewModel
import com.wguproject.videogamebacklog.ui.screens.LogView
import com.wguproject.videogamebacklog.ui.screens.login.LoginView
import com.wguproject.videogamebacklog.ui.screens.login.LogoutView
import com.wguproject.videogamebacklog.ui.screens.login.PostLoginView
import com.wguproject.videogamebacklog.ui.screens.recommendations.RecommendedGamesScreen
import com.wguproject.videogamebacklog.ui.screens.recommendations.RecommendedGamesViewModel
import com.wguproject.videogamebacklog.ui.screens.search.SearchDetailScreen
import com.wguproject.videogamebacklog.ui.screens.search.SearchScreen
import com.wguproject.videogamebacklog.ui.screens.search.SearchViewModel

@Composable
fun Navigation(
    viewModel: GameViewModel = viewModel(),
    searchViewModel: SearchViewModel = viewModel(),
    completedLogViewModel: CompletedLogViewModel = viewModel(),
    recommendedGamesViewModel: RecommendedGamesViewModel = viewModel(),
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
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                }
            )
        ) {
            val id = if (it.arguments != null) it.arguments!!.getInt("id") else 0
            AddEditDetailView(id = id, viewModel = viewModel, navController = navController)
        }
        composable(Screen.SearchScreen.route){
            SearchScreen(navController = navController, viewModel = searchViewModel)
        }
        composable(route = Screen.SearchDetailScreen.route + "/{id}",
            arguments = listOf(
                        navArgument("id"){type = NavType.IntType}
            )
        ){
            val id = if (it.arguments != null) it.arguments!!.getInt("id") else 0
            SearchDetailScreen(
                id = id,
                viewModel = searchViewModel,
                navController = navController
            )
        }
        composable(route = Screen.CompletedLogScreen.route){
            CompletedLogScreen(navController = navController,completedLogViewModel)
        }
        composable(route = Screen.RecommendedGamesScreen.route){
            RecommendedGamesScreen(navController = navController, viewModel = recommendedGamesViewModel)
        }
        composable(route = Screen.SignOutScreen.route){
            LogoutView(navController = navController, gameViewModel = viewModel)
        }

    }
}