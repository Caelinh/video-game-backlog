package com.wguproject.videogamebacklog

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthFetchSessionOptions
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.configuration.AmplifyOutputs
import com.amplifyframework.core.configuration.AmplifyOutputsData
import com.amplifyframework.ui.authenticator.ui.Authenticator
import com.wguproject.videogamebacklog.ui.navigation.Navigation
import com.wguproject.videogamebacklog.ui.theme.VideoGameBacklogTheme
import com.wguproject.videogamebacklog.utils.AppTitle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureAmplify()
        setContent {
            VideoGameBacklogTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter,) {
                        Authenticator(Modifier.padding(top = 50.dp),headerContent = {AppTitle()}){
                            Navigation()
                        }
                    }

                }
            }
        }
    }

    private fun configureAmplify() {
        try {
            // Check if Amplify is already configured
            Amplify.Auth.getPlugin("awsCognitoAuthPlugin")
        } catch (e: AmplifyException) {
            // Amplify is not configured, so we can configure it
            try {
                Amplify.addPlugin(AWSCognitoAuthPlugin())
                Amplify.addPlugin(AWSApiPlugin())
                Amplify.configure(AmplifyOutputs(R.raw.amplify_outputs), applicationContext)
                Log.i("MyAmplifyApp", "Initialized Amplify")
            } catch (error: AmplifyException) {
                Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
            }
        }
    }
}
