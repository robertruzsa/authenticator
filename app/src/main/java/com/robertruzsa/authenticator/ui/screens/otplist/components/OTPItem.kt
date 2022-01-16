package com.robertruzsa.authenticator.ui.screens.otplist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robertruzsa.authenticator.domain.model.Account

@Preview
@Composable
fun OTPItem(
    account: Account = Account("accoune name", "123456")
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(text = account.accountName)
            Text(text = account.code)
        }
    }
}
