package com.robertruzsa.ui.components.multifab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalAnimationApi
@Composable
fun MultiFloatingActionButton(
    fabIcon: ImageVector,
    items: List<MultiFabItem>,
    showLabels: Boolean = true
) {

    var fabState by remember { mutableStateOf(MultiFabState.COLLAPSED) }

    Column(horizontalAlignment = Alignment.End) {
        items.forEach { item ->
            AnimatedVisibility(visible = fabState == MultiFabState.EXPANDED) {
                MiniFloatingActionButton(
                    item = item,
                    showLabels = showLabels,
                    onFabItemClicked = item.onClick
                )
            }
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

@ExperimentalAnimationApi
@Composable
private fun MiniFloatingActionButton(
    item: MultiFabItem,
    showLabels: Boolean,
    onFabItemClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .padding(end = 10.dp),
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
