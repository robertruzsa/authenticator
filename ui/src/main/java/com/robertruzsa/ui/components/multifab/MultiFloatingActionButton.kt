package com.robertruzsa.ui.components.multifab

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MultiFloatingActionButton(
    fabIcon: ImageVector,
    items: List<MultiFabItem>,
    showLabels: Boolean = true
) {

    var fabState by remember { mutableStateOf(MultiFabState.COLLAPSED) }

    val transition: Transition<MultiFabState> =
        updateTransition(targetState = fabState, label = "transition")

    val alpha: Float by transition.animateFloat(
        label = "alpha",
        transitionSpec = {
            tween(durationMillis = 80)
        }
    ) { state ->
        when (state) {
            MultiFabState.COLLAPSED -> 0f
            MultiFabState.EXPANDED -> 1f
        }
    }
    Column(horizontalAlignment = Alignment.End) {
        items.forEach { item ->
            MiniFloatingActionButton(
                item = item,
                alpha = alpha,
                showLabels = showLabels,
                onFabItemClicked = item.onClick
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        FloatingActionButton(
            onClick = {
                fabState = when (fabState) {
                    MultiFabState.COLLAPSED -> MultiFabState.EXPANDED
                    MultiFabState.EXPANDED -> MultiFabState.COLLAPSED
                }
            }
        ) {
            Icon(
                imageVector = when (fabState) {
                    MultiFabState.COLLAPSED -> fabIcon
                    MultiFabState.EXPANDED -> Icons.Filled.Close
                },
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun MiniFloatingActionButton(
    item: MultiFabItem,
    alpha: Float,
    showLabels: Boolean,
    onFabItemClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(end = 10.dp)
            .alpha(alpha),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showLabels) {
            Card {
                Text(
                    text = item.label,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.surface)
                        .padding(8.dp)
                )
            }
        }
        FloatingActionButton(
            modifier = Modifier.size(40.dp),
            onClick = { onFabItemClicked() },
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
            )
        }
    }
}
