package com.robertruzsa.authenticator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.robertruzsa.authenticator.ui.navigation.Argument
import com.robertruzsa.authenticator.ui.navigation.Screen
import com.robertruzsa.authenticator.ui.screens.otplist.ListScreen
import com.robertruzsa.authenticator.ui.screens.otplist.OTPListViewModel
import com.robertruzsa.authenticator.ui.screens.qrreader.QRReaderScreen
import com.robertruzsa.authenticator.ui.theme.AuthenticatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                val factory = HiltViewModelFactory(LocalContext.current, backStackEntry)
                val viewModel: OTPListViewModel = viewModel(::OTPListViewModel.name, factory)
                ListScreen(
                    viewModel = viewModel,
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
