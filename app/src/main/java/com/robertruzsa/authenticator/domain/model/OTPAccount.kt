package com.robertruzsa.authenticator.domain.model

import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordConfig

sealed class OTPAccount(
    open val id: Int,
    open val issuer: String,
    open val accountName: String,
    open val secret: String,
    open val config: HmacOneTimePasswordConfig
) {
    data class TOTPAccount(
        override val id: Int = 0,
        override val issuer: String,
        override val accountName: String,
        override val secret: String,
        override val config: TimeBasedOneTimePasswordConfig
    ) : OTPAccount(id, issuer, accountName, secret, config)

    data class HOTPAccount(
        override val id: Int = 0,
        override val issuer: String,
        override val accountName: String,
        override val secret: String,
        val counter: Long,
        override val config: HmacOneTimePasswordConfig
    ) : OTPAccount(id, issuer, accountName, secret, config)
}
