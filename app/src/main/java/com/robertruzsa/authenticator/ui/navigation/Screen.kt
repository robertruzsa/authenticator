package com.robertruzsa.authenticator.ui.navigation

sealed class Screen(val route: String) {
    object OTPList : Screen("otp_list") {
        val fullRoute = "$route/{${Argument.QRData.key}}"
    }
    object OTPForm : Screen("otp_form")
    object QRReader : Screen("qr_reader")
}

sealed class Argument(val key: String) {
    object QRData : Argument("qr_data")
}
