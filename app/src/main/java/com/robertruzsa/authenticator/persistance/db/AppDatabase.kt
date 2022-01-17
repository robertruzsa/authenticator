package com.robertruzsa.authenticator.persistance.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.robertruzsa.authenticator.persistance.db.dao.OTPAccountDao
import com.robertruzsa.authenticator.persistance.db.entity.OTPAccountEntity

@Database(
    entities = [OTPAccountEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun otpAccountDao(): OTPAccountDao

    companion object {
        const val DB_NAME: String = "app_db"
    }
}
