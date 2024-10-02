package com.wguproject.videogamebacklog.data


import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.wguproject.videogamebacklog.utils.ListIntConverter

@Database(
    entities = [Game::class],
    version = 3,
    exportSchema =false,
)
@TypeConverters(ListIntConverter::class)
abstract class GameDatabase: RoomDatabase() {
    abstract fun gameDao() :GameDao

}