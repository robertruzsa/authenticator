package com.robertruzsa.authenticator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.robertruzsa.authenticator.ui.navigation.Argument
import com.robertruzsa.authenticator.ui.navigation.Screen
import com.robertruzsa.authenticator.ui.screens.otplist.ListScreen
import com.robertruzsa.authenticator.ui.screens.qrreader.QRReaderScreen
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
        val qrDataKey = Argument.QRData.key
        NavHost(
            navController = navController,
            startDestination = Screen.OTPList.route
        ) {
            composable(
                route = Screen.OTPList.route,
                arguments = listOf(
                    navArgument(qrDataKey) {
                    }
                )
            ) { backStackEntry ->
                ListScreen(
                    qrData = backStackEntry.arguments?.getString(qrDataKey),
                    onButtonClick = {
                        navController.navigate(route = Screen.OTPForm.route)
                    }
                )
            }
            composable(
                route = Screen.OTPForm.route
            ) {
                QRReaderScreen(navController)
            }
        }
    }
}
