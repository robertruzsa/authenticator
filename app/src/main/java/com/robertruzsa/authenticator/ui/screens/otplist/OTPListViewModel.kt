package com.robertruzsa.authenticator.ui.screens.otplist

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertruzsa.authenticator.domain.model.OTPAccount
import com.robertruzsa.authenticator.repository.OTPAccountRepository
import com.robertruzsa.authenticator.util.OTPUriResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OTPListViewModel @Inject constructor(
    private val otpAccountRepository: OTPAccountRepository
) : ViewModel() {

    val otpList: MutableState<List<OTPAccount>> = mutableStateOf(emptyList())

    init {
        getAllOtpAccounts()
    }

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
        otpAccount?.let {
            viewModelScope.launch {
                otpAccountRepository.insertOTPAccount(otpAccount)
                getAllOtpAccounts()
            }
        }
    }

    private fun getAllOtpAccounts() {
        viewModelScope.launch {
            otpList.value = otpAccountRepository.getAllOtpAccounts()
        }
    }
}
