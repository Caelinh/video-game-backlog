package com.wguproject.videogamebacklog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


@Composable
fun AddEditDetailView(
    id: Long, viewModel: GameViewModel, navController: NavController
) {
    Scaffold(topBar = {
        AppBarView(
            title = if (id != 0L) stringResource(id = R.string.update_game)
            else stringResource( id = R.string.add_game)
        ){ navController.navigateUp() }
    }) {
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
            })
            Spacer(Modifier.height(10.dp))
            GameTextField(label = "Description",
                value = viewModel.gameDescriptionState,
                onValueChanged = {
                    viewModel.onGameDescriptionChanged(it)
                })
            Spacer(Modifier.height(10.dp))
            Button(onClick = {
                if (viewModel.gameTitleState.isNotEmpty() && viewModel.gameDescriptionState.isNotEmpty()) {
                    //TODO Update game
                } else {
                    // TODO Add Game
                }
            }) {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_game)
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
        modifier = Modifier.fillMaxWidth(),
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