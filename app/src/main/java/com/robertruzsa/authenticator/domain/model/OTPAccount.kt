package com.robertruzsa.authenticator.domain.model

import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordConfig
import java.util.concurrent.TimeUnit

sealed class OTPAccount(
    open val id: Int,
    open val issuer: String,
    open val accountName: String,
    open val secret: String,
    open val config: HmacOneTimePasswordConfig
) {
    data class TOTPAccount(
        override val id: Int = 0,
        override val issuer: String = "TOTP Issuer",
        override val accountName: String = "TOTP account name",
        override val secret: String = "TOTP secret",
        override val config: TimeBasedOneTimePasswordConfig = TimeBasedOneTimePasswordConfig(
            timeStep = 30,
            timeStepUnit = TimeUnit.SECONDS,
            codeDigits = 6,
            hmacAlgorithm = HmacAlgorithm.SHA1
        )
    ) : OTPAccount(id, issuer, accountName, secret, config)

    data class HOTPAccount(
        override val id: Int = 0,
        override val issuer: String = "HOTP Issuer",
        override val accountName: String = "HOTP account name",
        override val secret: String = "HOTP secret",
        val counter: Long = 0,
        override val config: HmacOneTimePasswordConfig = HmacOneTimePasswordConfig(
            codeDigits = 6,
            hmacAlgorithm = HmacAlgorithm.SHA1
        )
    ) : OTPAccount(id, issuer, accountName, secret, config)
}
