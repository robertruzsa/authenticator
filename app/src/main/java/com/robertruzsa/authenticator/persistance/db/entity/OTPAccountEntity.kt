package com.robertruzsa.authenticator.persistance.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.robertruzsa.authenticator.domain.model.OTPType
import dev.turingcomplete.kotlinonetimepassword.HmacAlgorithm

@Entity(tableName = "otp_accounts")
data class OTPAccountEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "issuer")
    var issuer: String,

    @ColumnInfo(name = "account_name")
    var accountName: String,

    @ColumnInfo(name = "secret")
    var secret: String,

    @ColumnInfo(name = "type")
    var type: OTPType,

    @ColumnInfo(name = "period")
    var period: Long?,

    @ColumnInfo(name = "number_of_digits")
    var numberOfDigits: Int,

    @ColumnInfo(name = "algorithm")
    var algorithm: HmacAlgorithm,

    @ColumnInfo(name = "counter")
    var counter: Long?
)
