package com.papierflieger.data.repository

import com.papierflieger.data.network.response.RegisterResponse
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource

interface AuthenticationRepository {
    suspend fun register(
        username: String, fullname: String, email: String, password: String
    ) : Resource<RegisterResponse>
    suspend fun login(email: String, password: String)
}

class BasicAuthRepoImpl(
    private val apiService: ApiService
) : AuthenticationRepository{
    override suspend fun register(
        username: String, fullname: String, email: String, password: String
    ) : Resource<RegisterResponse> {
        return try {
            val response = apiService.register(username, fullname, email, password)
            if (response.status.isNullOrEmpty()) {
                Resource.Success(response)
            } else {
                Resource.Success(response)
            }
        } catch (e : Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun login(email: String, password: String) {
        TODO("Not yet implemented")
    }

}

// class GmailAuthImpl

// class FacebookAuthImpl