package com.robertruzsa.ui.components.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    enabled: Boolean = true,
    isReadOnly: Boolean = false,
    onClick: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
    isValid: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: KeyboardActionScope.() -> Unit = {},
    emptyError: String? = null,
    validationError: String? = null,
    showError: Boolean = false,
) {

    var isFocusedDirty by remember { mutableStateOf(false) }
    var showErrorOnFocusChange by remember { mutableStateOf(false) }

    val displayError = showError || showErrorOnFocusChange

    Box {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedTextField(
                    isError = if (emptyError != null) {
                        displayError && (!isValid || value.isBlank())
                    } else {
                        displayError && !isValid
                    },
                    enabled = enabled,
                    modifier = modifier.onFocusChanged { focusState ->
                        val focused = focusState.isFocused
                        if (focused) isFocusedDirty = true
                        if (!focused) {
                            if (isFocusedDirty) {
                                showErrorOnFocusChange = true
                            }
                        }
                    },
                    label = {
                        Text(
                            text = label,
                        )
                    },
                    value = value,
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    onValueChange = {
                        onValueChange(it)
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = imeAction,
                    ),
                    keyboardActions = KeyboardActions(
                        onAny = onImeAction
                    ),
                )
            }

            if (displayError) {
                when {
                    value.isBlank() -> {
                        emptyError?.let {
                            TextFieldError(it)
                        }
                    }

                    !isValid -> {
                        validationError?.let {
                            TextFieldError(it)
                        }
                    }
                }
            }
        }

        if (isReadOnly) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .alpha(0f)
                    .clickable(onClick = onClick)
            )
        }
    }
}
