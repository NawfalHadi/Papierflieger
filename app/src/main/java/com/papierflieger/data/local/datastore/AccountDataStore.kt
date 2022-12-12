package com.papierflieger.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountDataStore(private val ctx: Context) {

    suspend fun loginSuccess(_token: String, _email: String){
        ctx.accountDataStore.edit {
            it[token] = _token
            it[email] = _email
        }
    }

    suspend fun syncProfile(_names: String, _image:String) {
        ctx.accountDataStore.edit {
            it[names] = _names
            it[image] = _image

            it[isSynced] = true
        }
    }

    fun getToken() : Flow<String> {
        return ctx.accountDataStore.data.map {
            it[token] ?: "token_not_found"
        }
    }

    companion object {
        private const val DATASTORE_NAME = "account_preference"

        private val names = stringPreferencesKey("names_key")
        private val email = stringPreferencesKey("email_key")
        private val image = stringPreferencesKey("image_key")
        private val token = stringPreferencesKey("token_key")

        private val isSynced = booleanPreferencesKey("isSynced_key")

        private val Context.accountDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}