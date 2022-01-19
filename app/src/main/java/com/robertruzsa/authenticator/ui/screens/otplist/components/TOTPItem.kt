package com.robertruzsa.authenticator.ui.screens.otplist.components

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = account.accountName,
                    fontSize = 18.sp
                )
                Text(
                    text = otpCode,
                    fontSize = 24.sp,
                    color = Color.Blue
                )
            }
            CountDownIndicator(
                currentProgress = progress,
                onTick = { progressUnit ->
                    progress += progressUnit
                },
                countDownFromMillis = periodMillis,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                color = Color.Blue
            )
        }
    }
}
