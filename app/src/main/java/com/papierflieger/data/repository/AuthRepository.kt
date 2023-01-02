package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.auth.LoginResponse
import com.papierflieger.data.network.response.auth.RegisterResponse
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(
    private val apiService: ApiService
) {
    private var loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    private var registerResponse : MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()

    fun login(
        email: String,
        password: String,
    ) : LiveData<Resource<LoginResponse>> {
        loginResponse.value = Resource.Error(Throwable(""))
        apiService.login(email, password).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val body = response.body()
                if (body != null) {
                    loginResponse.value = Resource.Success(body)
                } else {
                    val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                    val errorMessage = errorJson?.optString("message")
                    loginResponse.value = Resource.Error(Throwable(errorMessage))
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResponse.value = Resource.Error(t)
            }
        })
        return loginResponse
    }

    fun register(
        username: String,
        fullName: String,
        email: String,
        password: String
    ) : LiveData<Resource<RegisterResponse>> {
        registerResponse.value = Resource.Error(Throwable(""))
        apiService.register(username, fullName, email, password).enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val body = response.body()
                if (body != null) {
                    registerResponse.value = Resource.Success(body)
                } else {
                    val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                    val errorMessage = errorJson?.optString("message")
                    registerResponse.value = Resource.Error(Throwable(errorMessage))
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                registerResponse.value = Resource.Error(t)
            }
        })
        return registerResponse
    }

}