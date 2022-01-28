package com.anonlatte.natgeo.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

sealed class ScreenState {
    object Error : ScreenState()
    object Success : ScreenState()
}

@Composable
fun ExampleScreen(state: ScreenState) {
    MaterialTheme {
        Box(
            modifier = Modifier.background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Welcome!",
                    style = MaterialTheme.typography.h3
                )
                Button(onClick = { /*TODO*/ }, enabled = state != ScreenState.Error) {
                    Text(
                        text = "Click me!",
                        style = MaterialTheme.typography.button
                    )
                    Icon(
                        imageVector = if (state == ScreenState.Error) {
                            Icons.Default.Warning
                        } else {
                            Icons.Default.Clear
                        },
                        contentDescription = null
                    )
                }
            }
            if (state == ScreenState.Error) {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun ErrorScreen() {
    MaterialTheme {
        Box(
            modifier = Modifier.background(
                color = Color.Red,
                shape = MaterialTheme.shapes.large
            )
        ) {
            Text(
                text = "Unexpected error",
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Preview
@Composable
fun ExampleScreenPrevSuccess() {
    ExampleScreen(state = ScreenState.Success)
}

@Preview
@Composable
fun ExampleScreenPrevError() {
    ExampleScreen(state = ScreenState.Error)
}
