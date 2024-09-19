package com.wguproject.videogamebacklog.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game-table")
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name="game-title")
    val title: String = "",
    @ColumnInfo(name = "game-description")
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