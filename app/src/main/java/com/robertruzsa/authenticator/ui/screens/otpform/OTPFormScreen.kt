package com.robertruzsa.authenticator.ui.screens.otpform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robertruzsa.ui.components.BaseButton
import com.robertruzsa.ui.components.TopBar
import com.robertruzsa.ui.components.textfield.BaseTextField

@Preview(showBackground = true)
@Composable
fun OTPFormScreen() {

    var accountNameText by remember { mutableStateOf("") }
    var otpSecretText by remember { mutableStateOf("") }

    val keyTextFieldFocusRequest = remember {
        FocusRequester()
    }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(
            navigationIcon = Icons.Filled.ArrowBack,
            title = "Enter account details"
        )
        BaseTextField(
            label = "Account name",
            value = accountNameText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .focusRequester(FocusRequester()),
            onImeAction = {
                keyTextFieldFocusRequest.requestFocus()
            },
            onValueChange = {
                accountNameText = it
            },
        )
        BaseTextField(
            label = "Key",
            value = otpSecretText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .focusRequester(keyTextFieldFocusRequest),
            onImeAction = {
                focusManager.clearFocus()
            },
            imeAction = ImeAction.Done,
            onValueChange = {
                otpSecretText = it
            },
        )
        BaseButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 16.dp
                ),
            text = "Add"
        )
    }
}
