package com.robertruzsa.authenticator.ui.screens.qrreader

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.NavController
import com.robertruzsa.authenticator.databinding.FragmentCodeReaderBinding
import com.robertruzsa.authenticator.ui.navigation.Argument
import com.robertruzsa.codereaderview.BarcodeFormat

@Composable
fun QRReaderScreen(
    navController: NavController
) {
    AndroidViewBinding(
        factory = FragmentCodeReaderBinding::inflate,
        modifier = Modifier.fillMaxSize()
    ) {
        codeReader.setOnBarcodeScannedListener(BarcodeFormat.QR_CODE) { qrData ->
            navController.previousBackStackEntry?.arguments?.putString(Argument.QRData.key, qrData)
            navController.popBackStack()
        }
    }
}
