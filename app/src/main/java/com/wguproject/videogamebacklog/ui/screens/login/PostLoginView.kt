package com.wguproject.videogamebacklog.ui.screens.login
import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.BackLog
import com.amplifyframework.datastore.generated.model.Game
import com.amplifyframework.datastore.generated.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


@Composable
fun PostLoginView(userId:String,navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }


}


suspend fun queryBacklogForUser(userId: String): Boolean? = suspendCancellableCoroutine { continuation ->
        Amplify.API.query(ModelQuery.get(User::class.java, userId),
            {
                Log.i("MyAmplifyApp", "Query results1 = ${(it.data)}")
            },
            {
                Log.e("MyAmplifyApp", "Query failed", it)
            }
        )
}

private suspend fun createBacklogForUser(userId: String) {
    val newUser = User.builder()
        .userId(userId)
        .build()

    Amplify.API.mutate(ModelMutation.create(newUser),
        {
            Log.i("MyAmplifyApp","User with ${userId}: ${it.data}")
            val newBacklog = BackLog.builder()
                .user(newUser)
                .games(listOf(Game.builder().build()))
                .build()
            Amplify.API.mutate(ModelMutation.create(newBacklog),
            {Log.i("MyAmplifyApp","Backlog with id:${it.data}")},
            {Log.i("MyAmplifyApp", "Create Failed", it)})

        },
        {
            Log.e("MyAmplifyApp", "Create failed", it)
        })

//TODO Find out how to get backlog created for user





}