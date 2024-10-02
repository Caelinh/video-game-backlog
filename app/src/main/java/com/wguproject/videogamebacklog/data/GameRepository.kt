package com.wguproject.videogamebacklog.data

import kotlinx.coroutines.flow.Flow

class GameRepository(private val gameDao: GameDao) {

    suspend fun addGame(game: Game){
        gameDao.addGame(game)
    }

    fun getAllGames(): Flow<List<Game>> = gameDao.getAllGames()
    fun getAllCompletedGames(): Flow<List<Game>> = gameDao.getAllCompletedGames()
    fun getAllBacklogGames(): Flow<List<Game>> = gameDao.getAllBacklogGames()

    fun getGameById(id:Int):Flow<Game> {
        return  gameDao.getGamebyId(id)
    }

    suspend fun updateGame(game: Game){
        gameDao.updateGame(game)
    }

    suspend fun deleteGame(game: Game){
        gameDao.deleteGame(game)
    }

}