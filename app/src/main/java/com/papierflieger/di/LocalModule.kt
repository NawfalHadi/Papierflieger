package com.papierflieger.di

import android.content.Context
import com.papierflieger.data.local.datastore.AccountDataStore
import com.papierflieger.data.local.datastore.SettingDataStore
import com.papierflieger.data.local.room.AppDatabase
import com.papierflieger.data.local.room.dao.AirportDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    // Data Strore

    @Provides
    @Singleton
    fun provideAccountDataStoreM(
        @ApplicationContext ctx: Context
    ) : AccountDataStore{
        return AccountDataStore(ctx)
    }

    @Provides
    @Singleton
    fun provideSettingsDataStore(
        @ApplicationContext ctx: Context
    ) : SettingDataStore {
        return SettingDataStore(ctx)
    }

    @Provides
    @Singleton
    fun provideDatasyncDataStore(
        @ApplicationContext ctx: Context
    ) : SettingDataStore {
        return SettingDataStore(ctx)
    }

    // ROOM Injection
//
//    @Provides
//    fun provideAppDatabase (
//        @ApplicationContext ctx: Context
//    ) : AppDatabase {
//        return AppDatabase.getInstance(ctx)
//    }
//
//    @Provides
//    fun provideAirportDao(
//        appDatabase: AppDatabase
//    ) : AirportDao {
//        return appDatabase.airportDao()
//    }

}