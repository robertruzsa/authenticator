package com.robertruzsa.authenticator.util

import android.net.Uri
import com.robertruzsa.authenticator.domain.model.OTPAccount
import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordConfig
import java.util.concurrent.TimeUnit

class OTPUriResolver {

    fun process(uri: Uri): OTPAccount? {
        if (uri.scheme != OTP_URI_SCHEME) {
            return null
        }
        val authority = uri.authority
        if (authority != TOTP_URI_AUTHORITY && authority != HOTP_URI_AUTHORITY) {
            return null
        }

        val issuerFromParam: String? = uri.getQueryParameter(OTP_URI_ISSUER)
        val path = uri.path ?: return null
        val label =
            path.drop(1) // dropping the first char, because path has the following format: "/alice@google.com"

        var issuerFromLabel: String? = null
        var accountNameFromLabel: String? = null
        try {
            val labelParts: List<String> = label.split(":")
            when (labelParts.size) {
                2 -> {
                    issuerFromLabel = labelParts[0]
                    accountNameFromLabel = labelParts[1]
                }
                1 -> {
                    accountNameFromLabel = label
                }
            }
        } catch (e: Exception) {
            return null
        }

        val accountName: String = accountNameFromLabel ?: return null
        val issuer: String = issuerFromParam ?: issuerFromLabel ?: ""

        val secret = uri.getQueryParameter(OTP_URI_SECRET) ?: return null // TODO: validate
        val digits = uri.getQueryParameter(TOTP_DIGITS)?.toIntOrNull() ?: DEFAULT_OTP_DIGITS
        val period = uri.getQueryParameter(TOTP_PERIOD)?.toLongOrNull() ?: DEFAULT_TOTP_PERIOD
        val algorithm = uri.getQueryParameter(TOTP_ALGORITHM) ?: return null
        val hmacAlgorithm = getHmacAlgorithm(algorithm) ?: return null
        val counter = uri.getQueryParameter(HOTP_COUNTER)?.toLongOrNull() ?: 0

        return when (authority) {
            TOTP_URI_AUTHORITY -> {
                OTPAccount.TOTPAccount(
                    issuer = issuer,
                    accountName = accountName,
                    secret = secret,
                    config = TimeBasedOneTimePasswordConfig(
                        timeStep = period,
                        timeStepUnit = TimeUnit.SECONDS,
                        codeDigits = digits,
                        hmacAlgorithm = hmacAlgorithm
                    )
                )
            }
            HOTP_URI_AUTHORITY -> {
                OTPAccount.HOTPAccount(
                    issuer = issuer,
                    accountName = accountName,
                    secret = secret,
                    counter = counter,
                    config = HmacOneTimePasswordConfig(
                        codeDigits = digits,
                        hmacAlgorithm = hmacAlgorithm
                    )
                )
            }
            else -> null
        }
    }

    fun getHmacAlgorithm(value: String): HmacAlgorithm? {
        return HmacAlgorithm.values().firstOrNull { hmacAlgorithm ->
            hmacAlgorithm.name == value
        }
    }

    companion object {
        const val OTP_URI_SCHEME = "otpauth"
        const val TOTP_URI_AUTHORITY = "totp"
        const val HOTP_URI_AUTHORITY = "hotp"
        const val OTP_URI_SECRET = "secret"
        const val OTP_URI_ISSUER = "issuer"
        const val HOTP_COUNTER = "counter"
        const val TOTP_PERIOD = "period"
        const val TOTP_ALGORITHM = "algorithm"
        const val TOTP_DIGITS = "digits"
        const val DEFAULT_TOTP_PERIOD = 30L
        const val DEFAULT_OTP_DIGITS = 6
    }
}
