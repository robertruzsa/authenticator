package com.robertruzsa.authenticator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.robertruzsa.authenticator.ui.navigation.Screen
import com.robertruzsa.authenticator.ui.screens.ListScreen
import com.robertruzsa.authenticator.ui.screens.NewItemScreen
import com.robertruzsa.authenticator.ui.theme.AuthenticatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthenticatorApp()
        }
    }
}

@Composable
fun AuthenticatorApp() {
    AuthenticatorTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.List.route
        ) {
            composable(
                route = Screen.List.route
            ) {
                ListScreen(
                    onButtonClick = {
                        navController.navigate(route = Screen.NewItem.route)
                    }
                )
            }
            composable(
                route = Screen.NewItem.route
            ) {
                NewItemScreen()
            }
        }
    }
}
