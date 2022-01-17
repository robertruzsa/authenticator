package com.robertruzsa.authenticator.ui.screens.otplist

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.robertruzsa.authenticator.domain.model.Account
import com.robertruzsa.authenticator.util.OTPAccount
import com.robertruzsa.authenticator.util.OTPUriResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordGenerator
import org.apache.commons.codec.binary.Base32
import javax.inject.Inject

@HiltViewModel
class OTPListViewModel @Inject constructor() : ViewModel() {

    val otpList: MutableState<List<Account>> = mutableStateOf(emptyList())

    fun handleAction(action: OTPListAction) {
        when (action) {
            is OTPListAction.QRCodeReceived -> {
                handleQrCode(action)
            }
        }
    }

    private fun handleQrCode(action: OTPListAction.QRCodeReceived) {
        val uri = Uri.parse(action.qrCodeData)
        val otpAccount = OTPUriResolver().process(uri) as? OTPAccount.TOTPAccount
        val uiModel = if (otpAccount != null) {
            Account(
                accountName = otpAccount.accountName,
                code = TimeBasedOneTimePasswordGenerator(
                    Base32().decode(otpAccount.secret),
                    otpAccount.config
                ).generate(System.currentTimeMillis())
            )
        } else {
            null
        }
        uiModel?.let {
            otpList.value = listOf(uiModel)
        }
    }
}
