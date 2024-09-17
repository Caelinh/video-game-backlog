package com.wguproject.videogamebacklog.data

data class Game(
    val id: Long = 0L,
    val title: String = "",
    val description: String = ""
)

object DummyGame{
    val backLog = listOf(
        Game(title = "Game1", description = "A really good game I can't wait to play."),
        Game(title = "Game2", description = "A really good game I can't wait to play."),
        Game(title = "Game3", description = "A really good game I can't wait to play."),
        Game(title = "Game4", description = "A really good game I can't wait to play."),
    )
}