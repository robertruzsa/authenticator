package com.robertruzsa.authenticator.util

import com.robertruzsa.authenticator.domain.model.OTPAccount
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordGenerator
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordGenerator
import org.apache.commons.codec.binary.Base32

object OTPCodeGenerator {

    fun generateTOTPCode(otpAccount: OTPAccount.TOTPAccount): String {
        val decodedSecret: ByteArray = Base32().decode(otpAccount.secret)
        return TimeBasedOneTimePasswordGenerator(
            decodedSecret,
            otpAccount.config
        ).generate(System.currentTimeMillis())
    }

    fun generateHOTPCode(otpAccount: OTPAccount.HOTPAccount): String {
        val decodedSecret: ByteArray = Base32().decode(otpAccount.secret)
        return HmacOneTimePasswordGenerator(
            decodedSecret,
            otpAccount.config
        ).generate(otpAccount.counter)
    }
}
