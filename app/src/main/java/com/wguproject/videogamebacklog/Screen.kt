package com.wguproject.videogamebacklog

sealed class Screen(val route:String) {
    object LoginScreen: Screen("login_screen")
    object HomeScreen: Screen("home_screen")
    object AddScreen: Screen("add_screen")
    object PostLoginScreen: Screen("post_login_screen")
    object SearchScreen: Screen("search_screen")
    object SearchDetailScreen: Screen("search_detail_screen")
}