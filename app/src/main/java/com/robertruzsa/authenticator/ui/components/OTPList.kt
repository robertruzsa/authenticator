package com.robertruzsa.authenticator.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.robertruzsa.authenticator.domain.model.Account

@Preview
@Composable
fun OTPList(
    otpList: List<Account> = listOf(
        Account("a", "122346"),
        Account("b", "222375")
    )
) {
    LazyColumn() {
        items(otpList) { account ->
            OTPItem(account)
        }
    }
}
