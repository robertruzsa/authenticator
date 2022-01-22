package com.robertruzsa.authenticator.ui.screens.otplist

import android.os.Bundle
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.robertruzsa.authenticator.ui.navigation.Argument
import com.robertruzsa.authenticator.ui.navigation.Screen
import com.robertruzsa.authenticator.ui.screens.otplist.components.OTPList
import com.robertruzsa.ui.components.TopBar
import com.robertruzsa.ui.components.multifab.MultiFabItem
import com.robertruzsa.ui.components.multifab.MultiFloatingActionButton

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
            MultiFloatingActionButton(
                fabIcon = Icons.Filled.Add,
                items = listOf(
                    MultiFabItem(
                        icon = Icons.Outlined.CameraAlt,
                        label = "QR-kód beolvasása",
                        onClick = {
                            navController.navigate(route = Screen.QRReader.route)
                        }
                    ),
                    MultiFabItem(
                        icon = Icons.Outlined.Keyboard,
                        label = "Kézi bevitel",
                        onClick = {
                            navController.navigate(route = Screen.OTPForm.route)
                        }
                    )
                )
            )
        }
    ) {
        OTPList(otpList)
    }
}
