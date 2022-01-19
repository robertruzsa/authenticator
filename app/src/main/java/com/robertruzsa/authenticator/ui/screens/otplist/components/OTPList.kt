package com.robertruzsa.authenticator.ui.screens.otplist.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robertruzsa.authenticator.domain.model.OTPAccount

@Preview
@Composable
fun OTPList(
    otpList: List<OTPAccount> = emptyList()
) {
    LazyColumn {
        itemsIndexed(otpList) { index, account ->
            when (account) {
                is OTPAccount.TOTPAccount -> TOTPItem(account)
                is OTPAccount.HOTPAccount -> HOTPItem(account)
            }
            if (index != otpList.lastIndex) {
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp)
                )
            }
        }
    }
}
