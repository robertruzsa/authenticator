package com.robertruzsa.authenticator.di

import androidx.room.Room
import com.robertruzsa.authenticator.BaseApplication
import com.robertruzsa.authenticator.persistance.db.AppDatabase
import com.robertruzsa.authenticator.persistance.db.dao.OTPAccountDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideOTPAccountDao(db: AppDatabase): OTPAccountDao {
        return db.otpAccountDao()
    }
}
