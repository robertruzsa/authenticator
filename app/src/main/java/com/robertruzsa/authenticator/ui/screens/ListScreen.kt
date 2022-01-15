package com.robertruzsa.authenticator.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import com.robertruzsa.authenticator.ui.components.TopBar

@Composable
fun ListScreen(
    onButtonClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = "Főoldal"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary,
                onClick = {
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = ""
                )
            }
        }
    ) {
        Column {
            Text(text = "list screen")
            Button(
                onClick = onButtonClick
            ) {
                Text("QR-kód beolvasása")
            }
        }
    }
}
