package com.robertruzsa.authenticator.ui.screens.qrreader

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.robertruzsa.authenticator.databinding.FragmentCodeReaderBinding
import com.robertruzsa.codereaderview.BarcodeFormat

@Preview
@Composable
fun QRReaderScreen() {
    AndroidViewBinding(
        factory = FragmentCodeReaderBinding::inflate,
        modifier = Modifier.fillMaxSize()
    ) {
        codeReader.setOnBarcodeScannedListener(BarcodeFormat.QR_CODE) { qrData ->
        }
    }
}
