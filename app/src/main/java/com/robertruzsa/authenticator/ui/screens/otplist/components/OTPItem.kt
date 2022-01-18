package com.robertruzsa.authenticator.ui.screens.otplist.components

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robertruzsa.authenticator.domain.model.OTPAccount
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordGenerator
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordGenerator
import org.apache.commons.codec.binary.Base32

@Preview
@Composable
fun OTPItem(
    account: OTPAccount? = null
) {

    var otpCode by remember {
        mutableStateOf(generateOTPCode(account!!))
    }

    DisposableEffect(Unit) {
        val handler = Handler(Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                otpCode = generateOTPCode(account!!)
                handler.postDelayed(this, 5_000)
            }
        }

        handler.post(runnable)

        onDispose {
            handler.removeCallbacks(runnable)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(text = account?.accountName ?: "")
            Text(text = otpCode)
        }
    }
}

private fun generateOTPCode(otpAccount: OTPAccount): String {
    val decodedSecret = Base32().decode(otpAccount.secret)

    return when (otpAccount) {
        is OTPAccount.TOTPAccount -> {
            TimeBasedOneTimePasswordGenerator(
                decodedSecret,
                otpAccount.config
            ).generate(System.currentTimeMillis())
        }
        is OTPAccount.HOTPAccount -> {
            HmacOneTimePasswordGenerator(
                decodedSecret,
                otpAccount.config
            ).generate(otpAccount.counter)
        }
    }
}
