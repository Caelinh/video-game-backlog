package com.wguproject.videogamebacklog.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.wguproject.videogamebacklog.utils.ListIntConverter

@Entity(tableName = "game-table")
@TypeConverters(ListIntConverter::class)
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "aggregated_rating")
    val aggregated_rating: Double? = null,
    @ColumnInfo(name = "category")
    val category: Int,
    @ColumnInfo(name = "coverUrl")
    val coverUrl: String? = null,
    @ColumnInfo(name = "first_release_date")
    val first_release_date: Long?,
    @ColumnInfo(name = "genres")
    val genres: List<Int>? = null,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name="similar_games")
    val similar_games: List<Int>? = null,
    @ColumnInfo(name = "summary")
    val summary: String? = null,
    @ColumnInfo(name = "completed")
    val completed: Boolean = false
)
