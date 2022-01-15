package com.robertruzsa.authenticator.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    title: String,
    navigationIcon: ImageVector? = null,
    onNavigationIconClick: () -> Unit = {},
    actionIcon: ImageVector? = null,
    onActionIconClick: () -> Unit = {},
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row {
                navigationIcon?.let {
                    IconButton(
                        onClick = onNavigationIconClick
                    ) {
                        Icon(
                            imageVector = navigationIcon,
                            contentDescription = null
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically),
                    text = title,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }

            actionIcon?.let {
                IconButton(
                    onClick = onActionIconClick,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.End)
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        tint = MaterialTheme.colors.secondary,
                        imageVector = actionIcon,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
