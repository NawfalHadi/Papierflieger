package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.UpdateUserResponse
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
  private val apiService : ApiService
) {

    private var updateUserResponse : MutableLiveData<Resource<UpdateUserResponse>> = MutableLiveData()

    fun updatePersonalInformation(
        token : String,
        title: String,
        username: String,
        fullname: String,
        birthdate: String,
        nationality: String
    ) : LiveData<Resource<UpdateUserResponse>> {
        apiService.updatePersonalProfile(
            token, title, username, fullname, birthdate, nationality
        ).enqueue(object : Callback<UpdateUserResponse>{
            override fun onResponse(
                call: Call<UpdateUserResponse>,
                response: Response<UpdateUserResponse>
            ) {
                if (response.isSuccessful){
                    updateUserResponse.postValue(Resource.Success(response.body()!!))
                }
            }

            override fun onFailure(call: Call<UpdateUserResponse>, t: Throwable) {
                updateUserResponse.postValue(Resource.Error(t))
            }
        })

        return updateUserResponse
    }

    fun updateAddressInformation(
        token : String,
        country: String,
        province: String,
        regency: String
    ) : LiveData<Resource<UpdateUserResponse>>{
        apiService.updateAddressProfile(token, country, province, regency).enqueue(
            object : Callback<UpdateUserResponse>{
                override fun onResponse(
                    call: Call<UpdateUserResponse>,
                    response: Response<UpdateUserResponse>
                ) {
                    updateUserResponse.postValue(Resource.Success(response.body()!!))
                }

                override fun onFailure(call: Call<UpdateUserResponse>, t: Throwable) {
                    updateUserResponse.postValue(Resource.Error(t))
                }
            }
        )

        return updateUserResponse
    }

}