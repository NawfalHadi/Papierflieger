package com.papierflieger.di

import android.content.Context
import com.papierflieger.data.local.datastore.AccountDataStore
import com.papierflieger.data.local.datastore.SettingDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

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

}