package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.DestinationsResponse
import com.papierflieger.data.network.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationRepository(
    private val apiService: ApiService
) {
    private var destinationLiveData : MutableLiveData<DestinationsResponse> = MutableLiveData()

    fun getDestination() : LiveData<DestinationsResponse> {
        apiService.destionations().enqueue(
            object : Callback<DestinationsResponse>{
                override fun onResponse(
                    call: Call<DestinationsResponse>,
                    response: Response<DestinationsResponse>
                ) {
                    if (response.isSuccessful){
                        destinationLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DestinationsResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            }
        )
        return destinationLiveData
    }
}