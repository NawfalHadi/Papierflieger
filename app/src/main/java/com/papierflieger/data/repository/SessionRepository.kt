package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.Profile
import com.papierflieger.data.network.response.UserResponse
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SessionRepository(
    private val apiService: ApiService
) {
    private var profileUserResponse : MutableLiveData<Resource<Profile>> = MutableLiveData()

    fun getProfileUser(token: String): LiveData<Resource<Profile>> {
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

//    fun getHistoryUser(token: String){
//
//    }
}