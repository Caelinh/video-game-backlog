package com.wguproject.videogamebacklog

import android.content.Context
import androidx.room.Room
import com.wguproject.videogamebacklog.data.GameDatabase
import com.wguproject.videogamebacklog.data.GameRepository

object Graph {
    lateinit var database: GameDatabase

    val gameRepository by lazy{
        GameRepository(gameDao = database.gameDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, GameDatabase::class.java,"vgb.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}