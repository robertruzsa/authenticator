package com.robertruzsa.authenticator.ui.screens.otplist

import android.os.Bundle
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.robertruzsa.authenticator.ui.navigation.Argument
import com.robertruzsa.authenticator.ui.navigation.Screen
import com.robertruzsa.authenticator.ui.screens.otplist.components.OTPList
import com.robertruzsa.ui.components.TopBar

@Composable
fun ListScreen(
    navController: NavController,
    viewModel: OTPListViewModel
) {

    val args: Bundle? = navController.currentBackStackEntry?.arguments
    val qrData: String? = args?.getString(Argument.QRData.key)
    qrData?.let {
        viewModel.handleAction(OTPListAction.QRCodeReceived(it))
        args.clear()
    }

    val otpList = viewModel.otpList.value

    Scaffold(
        topBar = {
            TopBar(
                title = "Hitelesítő kódok",
                elevation = 0.dp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary,
                onClick = {
                    navController.navigate(route = Screen.OTPForm.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = ""
                )
            }
        }
    ) {
        OTPList(otpList)
    }
}
