package com.robertruzsa.authenticator.ui.screens.otplist

import android.net.Uri
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import com.robertruzsa.authenticator.domain.model.Account
import com.robertruzsa.authenticator.ui.screens.otplist.components.OTPList
import com.robertruzsa.authenticator.util.OTPAccount
import com.robertruzsa.authenticator.util.OTPUriResolver
import com.robertruzsa.ui.components.TopBar
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordGenerator
import org.apache.commons.codec.binary.Base32

@Composable
fun ListScreen(
    onButtonClick: () -> Unit,
    qrData: String? = null,
    viewModel: OTPListViewModel
) {

    val otpAccount = if (qrData != null) {
        val uri = Uri.parse(qrData)
        OTPUriResolver().process(uri) as? OTPAccount.TOTPAccount
    } else {
        null
    }

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

    Scaffold(
        topBar = {
            TopBar(
                title = "Főoldal"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary,
                onClick = {
                    onButtonClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = ""
                )
            }
        }
    ) {
        OTPList(
            otpList = if (uiModel != null) {
                listOf(uiModel)
            } else {
                emptyList()
            }
        )
    }
}
