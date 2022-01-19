package com.robertruzsa.authenticator.ui.screens.otplist.components

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robertruzsa.authenticator.domain.model.OTPAccount
import com.robertruzsa.ui.components.CountDownIndicator
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordGenerator
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordGenerator
import org.apache.commons.codec.binary.Base32

@Preview
@Composable
fun OTPItem(
    account: OTPAccount? = null
) {
    when (account) {
        is OTPAccount.TOTPAccount -> TOTPItem(account)
        is OTPAccount.HOTPAccount -> HOTPItem(account)
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

@Composable
fun TOTPItem(account: OTPAccount.TOTPAccount) {
    var otpCode by remember {
        mutableStateOf(generateOTPCode(account))
    }

    var progress by remember {
        mutableStateOf(0f)
    }

    val periodMillis: Long = account.config.timeStep * 1000

    DisposableEffect(Unit) {
        val handler = Handler(Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                otpCode = generateOTPCode(account)
                progress = 0f
                handler.postDelayed(this, periodMillis)
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
            Text(text = account.accountName)
            Text(text = otpCode)
            CountDownIndicator(
                currentProgress = progress,
                onTick = { progressUnit ->
                    progress += progressUnit
                },
                countDownFromMillis = periodMillis,
                modifier = Modifier.size(20.dp),
                color = Color.Blue
            )
        }
    }
}

@Composable
fun HOTPItem(account: OTPAccount.HOTPAccount) {
    // TODO
}
