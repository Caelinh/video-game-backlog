package com.wguproject.videogamebacklog.data

import android.util.Log
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class GameRepository(private val gameDao: GameDao) {

    suspend fun addGame(game: Game){
        gameDao.addGame(game)
    }

    fun getAllGames(): Flow<List<Game>> = gameDao.getAllGames()

    fun getAllComparisonGames(): Flow<List<Game>> = gameDao.getAllGamesForComparison()

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

    suspend fun findAWSUser(userId: String): User? = suspendCancellableCoroutine { continuation ->
        Amplify.API.query(
            ModelQuery.get(User::class.java, userId),
            { response ->
                if (response.hasData()) {
                    val user = response.data as? User
                    if (user != null) {
                        Log.i("MyAmplifyApp", "Query results = $user")
                        continuation.resume(user)
                    } else {
                        Log.w("MyAmplifyApp", "Query returned null user")
                        continuation.resume(null)
                    }
                } else {
                    Log.w("MyAmplifyApp", "Query has no data")
                    continuation.resume(null)
                }
            },
            { error ->
                Log.e("MyAmplifyApp", "Query failed", error)
                continuation.resume(null)
            }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun createAWSDBUser(userId: String): User? = suspendCancellableCoroutine { continuation ->
        val newUser = User.builder()
            .userId(userId)
            .build()

        Amplify.API.mutate(
            ModelMutation.create(newUser),
            { response ->
                if (response.hasData()) {
                    val createdUser = response.data
                    Log.i("Amplify User Created", "User created with id: ${createdUser.userId}")
                    continuation.resume(createdUser)
                } else {
                    Log.w("Amplify User Created", "Mutation response has no data")
                    continuation.resume(null)
                }
            },
            { error ->
                Log.e("Amplify User Creation", "Unable to create a user", error)
                continuation.resume(null)
            }
        )
    }

}