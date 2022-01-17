package com.robertruzsa.authenticator.repository

import com.robertruzsa.authenticator.domain.model.OTPAccount
import com.robertruzsa.authenticator.persistance.db.dao.OTPAccountDao
import com.robertruzsa.authenticator.persistance.db.mapper.OTPAccountEntityMapper
import javax.inject.Inject

class OTPAccountRepository @Inject constructor(
    private val otpAccountDao: OTPAccountDao
) {

    private val mapper = OTPAccountEntityMapper()

    suspend fun insertOTPAccount(otpAccount: OTPAccount) {
        otpAccountDao.insertOTPAccount(
            mapper.mapFromDomainModel(otpAccount)
        )
    }

    suspend fun getAllOtpAccounts(): List<OTPAccount> {
        return otpAccountDao.getAllOTPAccounts().map {
            mapper.mapToDomainModel(it)
        }
    }
}
