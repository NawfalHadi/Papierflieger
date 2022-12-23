package com.papierflieger.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatasyncDataStore(private val ctx : Context) {

    //Airport Data
    suspend fun syncAirport() {
        ctx.accountDataStore.edit {
            it[airport] = true
        }
    }

    fun isAirportSynced() : Flow<Boolean> {
        return ctx.accountDataStore.data.map {
            it[airport] ?: false
        }
    }

    companion object {
        private const val DATASTORE_NAME = "datasync_preference"

        private val airport = booleanPreferencesKey("airport_key")

        private val Context.accountDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}