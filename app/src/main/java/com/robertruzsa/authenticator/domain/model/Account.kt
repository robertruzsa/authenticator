package com.robertruzsa.authenticator.domain.model

data class Account(
    val accountName: String,
    val code: String
)

enum class OTPType {
    TOTP,
    HOTP
}
