package com.robertruzsa.authenticator.ui.screens.otplist.components

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robertruzsa.authenticator.domain.model.OTPAccount
import com.robertruzsa.authenticator.util.OTPCodeGenerator
import com.robertruzsa.ui.components.CountDownIndicator

@Preview(showBackground = true)
@Composable
fun TOTPItem(account: OTPAccount.TOTPAccount = OTPAccount.TOTPAccount()) {
    var otpCode by remember {
        mutableStateOf(OTPCodeGenerator.generateTOTPCode(account))
    }

    var progress by remember {
        mutableStateOf(0f)
    }

    val periodMillis: Long = account.config.timeStep * 1000

    DisposableEffect(Unit) {
        val handler = Handler(Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                otpCode = OTPCodeGenerator.generateTOTPCode(account)
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
