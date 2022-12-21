package com.papierflieger.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AccountDataStore(private val ctx: Context) {

    // After Login

    suspend fun loginSuccess(_token: String, _email: String, _names: String, _image: String, _role: String){
        ctx.accountDataStore.edit {
            it[token] = _token
            it[email] = _email
            it[names] = _names
            it[image] = _image
            it[role] = _role
        }
    }

    suspend fun logout() {
        ctx.accountDataStore.edit {
            it[token] = ""
            it[email] = ""
            it[names] = ""
            it[image] = ""
            it[role] = ""
        }
    }

    fun getToken() : Flow<String> {
        return ctx.accountDataStore.data.map {
            it[token] ?: ""
        }
    }

    fun getNames() : Flow<String> {
        return ctx.accountDataStore.data.map {
            it[names] ?: ""
        }
    }

    fun getAvatar(): Flow<String> {
        return ctx.accountDataStore.data.map {
            it[image] ?: ""
        }
    }

    fun getEmail(): Flow<String> {
        return ctx.accountDataStore.data.map {
            it[email] ?: ""
        }
    }

    fun getRole(): Flow<String> {
        return ctx.accountDataStore.data.map {
            it[role] ?: ""
        }
    }

    companion object {
        private const val DATASTORE_NAME = "account_preference"

        private val names = stringPreferencesKey("names_key")
        private val email = stringPreferencesKey("email_key")
        private val image = stringPreferencesKey("image_key")
        private val token = stringPreferencesKey("token_key")
        private val role = stringPreferencesKey("role_key")

        private val Context.accountDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}
