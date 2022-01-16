package com.robertruzsa.authenticator.ui.screens.otplist

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import com.robertruzsa.authenticator.ui.screens.otplist.components.OTPList
import com.robertruzsa.ui.components.TopBar

@Composable
fun ListScreen(
    onButtonClick: () -> Unit,
    qrData: String? = null
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
                    onButtonClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = ""
                )
            }
        }
    ) {
        OTPList()
    }
}
