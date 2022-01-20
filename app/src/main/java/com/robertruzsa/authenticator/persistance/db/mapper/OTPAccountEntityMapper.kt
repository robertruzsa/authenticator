package com.robertruzsa.authenticator.persistance.db.mapper

import com.robertruzsa.authenticator.domain.mapper.DomainMapper
import com.robertruzsa.authenticator.domain.model.OTPAccount
import com.robertruzsa.authenticator.domain.model.OTPType
import com.robertruzsa.authenticator.persistance.db.entity.OTPAccountEntity
import dev.turingcomplete.kotlinonetimepassword.HmacOneTimePasswordConfig
import dev.turingcomplete.kotlinonetimepassword.TimeBasedOneTimePasswordConfig
import java.util.concurrent.TimeUnit

class OTPAccountEntityMapper : DomainMapper<OTPAccountEntity, OTPAccount> {
    override fun mapToDomainModel(model: OTPAccountEntity): OTPAccount {
        return when (model.type) {
            OTPType.TOTP -> {
                OTPAccount.TOTPAccount(
                    // id = model.id,
                    issuer = model.issuer,
                    accountName = model.accountName,
                    secret = model.secret,
                    config = TimeBasedOneTimePasswordConfig(
                        timeStep = model.period ?: 0,
                        timeStepUnit = TimeUnit.SECONDS,
                        codeDigits = model.numberOfDigits,
                        hmacAlgorithm = model.algorithm
                    )
                )
            }
            OTPType.HOTP -> {
                OTPAccount.HOTPAccount(
                    // id = model.id,
                    issuer = model.issuer,
                    accountName = model.accountName,
                    secret = model.secret,
                    counter = model.counter ?: 0,
                    config = HmacOneTimePasswordConfig(
                        codeDigits = model.numberOfDigits,
                        hmacAlgorithm = model.algorithm
                    )
                )
            }
        }
    }

    override fun mapFromDomainModel(domainModel: OTPAccount): OTPAccountEntity {
        return when (domainModel) {
            is OTPAccount.TOTPAccount -> {
                OTPAccountEntity(
                    type = OTPType.TOTP,
                    issuer = domainModel.issuer,
                    accountName = domainModel.accountName,
                    secret = domainModel.secret,
                    algorithm = domainModel.config.hmacAlgorithm,
                    period = domainModel.config.timeStep,
                    counter = null,
                    numberOfDigits = domainModel.config.codeDigits,
                )
            }
            is OTPAccount.HOTPAccount -> {
                OTPAccountEntity(
                    type = OTPType.HOTP,
                    issuer = domainModel.issuer,
                    accountName = domainModel.accountName,
                    secret = domainModel.secret,
                    algorithm = domainModel.config.hmacAlgorithm,
                    period = null,
                    counter = domainModel.counter,
                    numberOfDigits = domainModel.config.codeDigits,
                )
            }
        }
    }
}
