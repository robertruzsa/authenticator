package com.robertruzsa.authenticator.ui.screens.otplist

sealed class OTPListAction {
    data class QRCodeReceived(val qrCodeData: String) : OTPListAction()
}
