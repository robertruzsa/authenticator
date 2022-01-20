package com.robertruzsa.ui.components

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CountDownIndicator(
    modifier: Modifier = Modifier,
    currentProgress: Float = 0f,
    onTick: (Float) -> Unit = {},
    countDownFromMillis: Long = 30_000,
    color: Color = Color.Blue
) {

    val sectorUnitSize: Float =
        FULL_ROTATION_DEGREES / (countDownFromMillis / REDRAW_INTERVAL_MILLIS)

    DisposableEffect(Unit) {
        val handler = Handler(Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                onTick(sectorUnitSize)
                handler.postDelayed(this, REDRAW_INTERVAL_MILLIS)
            }
        }

        handler.post(runnable)

        onDispose {
            handler.removeCallbacks(runnable)
        }
    }

    Box(
        modifier = modifier
    ) {
        Canvas(
            modifier = modifier
        ) {
            drawArc(
                color = color,
                startAngle = START_ANGLE,
                sweepAngle = currentProgress,
                useCenter = true,
            )
        }
    }
}

const val FULL_ROTATION_DEGREES: Float = 360f
const val START_ANGLE: Float = -90f
const val REDRAW_INTERVAL_MILLIS: Long = 50
