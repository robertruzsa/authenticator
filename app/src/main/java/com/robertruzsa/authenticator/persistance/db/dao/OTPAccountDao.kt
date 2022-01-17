package com.robertruzsa.authenticator.persistance.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.robertruzsa.authenticator.persistance.db.entity.OTPAccountEntity

@Dao
interface OTPAccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOTPAccount(account: OTPAccountEntity)

    @Query("DELETE FROM otp_accounts WHERE id = :id")
    suspend fun deleteOTPAccount(id: Int)

    @Query("SELECT * FROM otp_accounts")
    suspend fun getAllOTPAccounts(): List<OTPAccountEntity>
}
