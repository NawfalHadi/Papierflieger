package com.papierflieger.data.datasource

import com.papierflieger.data.network.response.RegisterResponse
import com.papierflieger.data.network.service.ApiService

interface AuthenticationDataSource {
    suspend fun register(username: String, fullname: String, email: String, password: String) : RegisterResponse
    suspend fun login(email: String, password: String)
}

class BasicAuthDatasourceImpl(
    private val apiService: ApiService
) : AuthenticationDataSource {
    override suspend fun register(
        username: String,
        fullname: String,
        email: String,
        password: String
    ) : RegisterResponse {
        return apiService.register(username, fullname, email, password)
    }

    override suspend fun login(email: String, password: String) {
        TODO("Not yet implemented")
    }

}