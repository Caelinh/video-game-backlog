package com.wguproject.videogamebacklog.ui.screens

import androidx.collection.emptyIntList
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wguproject.videogamebacklog.ui.screens.AppBarView
import com.wguproject.videogamebacklog.GameViewModel
import com.wguproject.videogamebacklog.R
import com.wguproject.videogamebacklog.Screen
import com.wguproject.videogamebacklog.data.Game
import kotlinx.coroutines.launch


@Composable
fun AddEditDetailView(
    id: Int, viewModel: GameViewModel, navController: NavController
) {

    val snackMessage = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    if (id != 0) {
        val game = navController.previousBackStackEntry?.savedStateHandle?.get<Game>("game")
        viewModel.gameTitleState = viewModel.selectedGame.name
        viewModel.gameDescriptionState = viewModel.selectedGame.summary.toString()
    } else {
        viewModel.gameTitleState = ""
        viewModel.gameDescriptionState = ""
    }

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            AppBarBottom(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(10.dp))
            GameTextField(label = "Title", value = viewModel.gameTitleState, onValueChanged = {
                viewModel.onGameTitleChanged(it)
            },)
            Spacer(Modifier.height(10.dp))
            GameTextField(label = "Summary",
                value = viewModel.gameDescriptionState,
                onValueChanged = {
                    viewModel.onGameDescriptionChanged(it)
                })
            Spacer(Modifier.height(10.dp))
            Button(onClick = {
                if (viewModel.gameTitleState.isNotEmpty() && viewModel.gameDescriptionState.isNotEmpty()) {
                    if (id != 0) {
                        viewModel.updateGame(viewModel.selectedGame)
                        snackMessage.value = "Game Updated"
                    } else {
                    }
                } else {
                    navController.navigate(Screen.SearchScreen.route)
                    snackMessage.value = "Enter fields to create a Game"
                }
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigate(Screen.HomeScreen.route)
                }
            }) {
                Text(
                    text = if (id != 0) stringResource(id = R.string.update_game)
                    else stringResource(
                        id = R.string.add_game
                    ), style = TextStyle(
                        fontSize = 18.sp
                    )
                )

            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTextField(
    label: String, value: String, onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            cursorColor = colorResource(id = R.color.black),
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            focusedLabelColor = colorResource(id = R.color.black),
            unfocusedLabelColor = colorResource(id = R.color.black),
        )
    )
}

@Preview
@Composable
fun GameTestFieldPrev() {
    GameTextField(label = "text", value = "text", onValueChanged = {})
}