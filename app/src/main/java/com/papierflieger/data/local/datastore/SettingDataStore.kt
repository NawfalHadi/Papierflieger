package com.papierflieger.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

class SettingDataStore(private val ctx: Context) {

    suspend fun firstOpen() {
        ctx.accountDataStore.edit {
            it[isFirst] = true
        }
    }

    fun isFirstOpen() : Flow<Boolean> {
        return ctx.accountDataStore.data.map {
            it[isFirst] ?: false
        }
    }

    companion object {
        private const val DATASTORE_NAME = "settings_preference"

        private val isFirst = booleanPreferencesKey("isFirst_key")

        private val Context.accountDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}