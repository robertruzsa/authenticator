package com.robertruzsa.authenticator.ui.screens.qrreader

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.NavController
import com.robertruzsa.authenticator.databinding.FragmentCodeReaderBinding
import com.robertruzsa.authenticator.ui.navigation.Screen
import com.robertruzsa.codereaderview.BarcodeFormat
import java.net.URLEncoder

@Composable
fun QRReaderScreen(
    navController: NavController
) {
    AndroidViewBinding(
        factory = FragmentCodeReaderBinding::inflate,
        modifier = Modifier.fillMaxSize()
    ) {
        codeReader.setOnBarcodeScannedListener(BarcodeFormat.QR_CODE) { qrData ->
            val escapedQRDataString = URLEncoder.encode(qrData, "UTF8")
            navController.navigate("${Screen.OTPList.route}/$escapedQRDataString") {
                popUpTo(Screen.OTPList.fullRoute) {
                    inclusive = true
                }
            }
        }
    }
}
