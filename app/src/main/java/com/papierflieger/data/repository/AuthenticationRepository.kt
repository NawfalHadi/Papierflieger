package com.papierflieger.data.repository

import com.papierflieger.data.datasource.AuthenticationDataSource
import com.papierflieger.data.network.response.RegisterResponse
import com.papierflieger.wrapper.Resource

interface AuthenticationRepository {
    suspend fun register(
        username: String, fullname: String, email: String, password: String
    ) : Resource<RegisterResponse>
    suspend fun login(email: String, password: String)
}

class BasicAuthRepoImpl(
    private val basicAuthDataSource: AuthenticationDataSource
) : AuthenticationRepository{
    override suspend fun register(
        username: String, fullname: String, email: String, password: String
    ) : Resource<RegisterResponse> {
        return try {
            val responsed = basicAuthDataSource.register(username, fullname, email, password)
            if(responsed.status.isNullOrEmpty()){
                Resource.Success(responsed)
            }else {
                Resource.Success(responsed)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun login(email: String, password: String) {
        TODO("Not yet implemented")
    }

}

// class GmailAuthImpl

// class FacebookAuthImpl