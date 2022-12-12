package com.papierflieger.data.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.papierflieger.data.network.response.LoginResponse
import com.papierflieger.data.network.response.RegisterResponse
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource

interface AuthenticationRepository {
    suspend fun register(
        username: String, fullname: String, email: String, password: String
    ) : Resource<RegisterResponse>
    suspend fun login(email: String, password: String) : Resource<LoginResponse>
}

class BasicAuthRepoImpl(
    private val apiService: ApiService
) : AuthenticationRepository{
    override suspend fun register(
        username: String, fullname: String, email: String, password: String
    ) : Resource<RegisterResponse> {
        return try {
            val response = apiService.register(username, fullname, email, password)
            if (!response.newUser?.avatar.isNullOrEmpty()) {
                Resource.Success(response)
            } else {
                Resource.Empty()
            }
        } catch (t : Throwable) {
            Log.e("400 Message", t.message.toString())
            Resource.Error(t)
        }
    }

    override suspend fun login(email: String, password: String) : Resource<LoginResponse>{
        return try {
            val response = apiService.login(email, password)
            if (!response.token.isNullOrEmpty()){
                Resource.Success(response)
            } else {
                Resource.Empty()
            }
        } catch (t: Throwable){
            Resource.Error(t)
        }
    }

}

// class GmailAuthImpl

// class FacebookAuthImpl