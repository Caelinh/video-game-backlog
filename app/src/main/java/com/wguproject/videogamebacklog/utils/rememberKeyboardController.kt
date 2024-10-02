package com.wguproject.videogamebacklog.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Composable
fun rememberKeyboardController(): () -> Unit{
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    return {
        keyboardController?.hide()
        focusManager.clearFocus()
    }
}