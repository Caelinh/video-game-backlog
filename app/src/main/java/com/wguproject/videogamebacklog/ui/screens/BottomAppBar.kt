package com.wguproject.videogamebacklog.ui.screens


import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wguproject.videogamebacklog.R
import com.wguproject.videogamebacklog.Screen
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun AppBarBottom(navController: NavController) {
    BottomAppBar(
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(1.0f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.size(60.dp),
                    onClick = { navController.navigate(Screen.HomeScreen.route) }) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Home, contentDescription = "Localized description")
                        Text(text = "Home", fontSize = 12.sp)
                    }
                    
                }
                IconButton(
                    modifier = Modifier.size(60.dp),
                    onClick = { navController.navigate(Screen.CompletedLogScreen.route) }) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription = "Localized description",
                        )
                        Text(text = "Complete", fontSize = 12.sp)
                    }
                }
                IconButton(modifier = Modifier.size(60.dp), onClick = { navController.navigate(Screen.RecommendedGamesScreen.route) }) {
                   Column(horizontalAlignment = Alignment.CenterHorizontally) {
                       Icon(
                           painter = painterResource(id = R.drawable.game_controller_filled),
                           contentDescription = "Localized description",
                       )
                       Text(text = "Discover", fontSize = 12.sp)
                   }
                }
                IconButton(modifier = Modifier.size(60.dp), onClick = { navController.navigate(Screen.SignOutScreen.route) }) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Filled.AccountBox,
                            contentDescription = "Localized description",
                        )
                        Text(text = "Account", fontSize = 12.sp)
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.SearchScreen.route) },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Add, "Localized description")
                }
            }
        }

    )
}