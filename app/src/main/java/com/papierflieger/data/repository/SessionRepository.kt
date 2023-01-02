package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.transaction.HistoriesResponse
import com.papierflieger.data.network.response.transaction.TransactionsResponse
import com.papierflieger.data.network.response.user.User
import com.papierflieger.data.network.response.user.UserResponse
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SessionRepository(
    private val apiService: ApiService
) {
    private var profileUserResponse : MutableLiveData<Resource<User>> = MutableLiveData()
    private var historiesResponse : MutableLiveData<Resource<HistoriesResponse>> = MutableLiveData()

    fun getProfileUser(token: String): LiveData<Resource<User>> {
        apiService.userProfile(token).enqueue(
            object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        val responsed = response.body()?.profile
                        if (responsed?.fullName.isNullOrEmpty()){
                            profileUserResponse.postValue(Resource.Empty())
                        } else {
                            profileUserResponse.postValue(Resource.Success(responsed!!))
                        }
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    profileUserResponse.postValue(Resource.Error(t))
                }
            }
        )
        return profileUserResponse
    }

    fun getHistoryUser(token: String) : LiveData<Resource<HistoriesResponse>>{
        apiService.getHistoriesUser(token).enqueue(
            object : Callback<HistoriesResponse>{
                override fun onResponse(
                    call: Call<HistoriesResponse>,
                    response: Response<HistoriesResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        historiesResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        historiesResponse.value = Resource.Error(Throwable(errorMessage))
                    }
                }
                override fun onFailure(call: Call<HistoriesResponse>, t: Throwable) {
                    historiesResponse.postValue(Resource.Error(t))
                }
            }
        )
        return historiesResponse
    }
}