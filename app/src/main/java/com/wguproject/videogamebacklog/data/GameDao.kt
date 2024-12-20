package com.wguproject.videogamebacklog.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class GameDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addGame(gameEntity: Game)

    @Query("Select * from `game-table`")
    abstract fun getAllGames(): Flow<List<Game>>
    @Query("Select * from `game-table`")
    abstract fun getAllGamesForComparison(): Flow<List<Game>>
    @Update
    abstract suspend fun updateGame(gameEntity: Game)

    @Delete
    abstract suspend fun deleteGame(gameEntity: Game)

    @Query("Select * from `game-table` where id = :id")
    abstract fun getGamebyId(id:Int): Flow<Game>

    @Query("Select * from `game-table` where completed = true")
    abstract fun getAllCompletedGames(): Flow<List<Game>>

    @Query("Select * from `game-table` where completed = false")
    abstract fun getAllBacklogGames(): Flow<List<Game>>


}