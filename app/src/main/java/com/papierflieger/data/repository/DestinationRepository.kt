package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.CreateDestinationResponse
import com.papierflieger.data.network.response.DestinationResponse
import com.papierflieger.data.network.response.DestinationsResponse
import com.papierflieger.data.network.response.UpdateDestinationResponse
import com.papierflieger.data.network.service.ApiAdminService
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Header

class DestinationRepository(
    private val apiService: ApiService
) {
    private var destinationsLiveData : MutableLiveData<DestinationsResponse> = MutableLiveData()
    private var destinationLiveData : MutableLiveData<DestinationResponse> = MutableLiveData()

    fun getDestination() : LiveData<DestinationsResponse> {
        apiService.destinations().enqueue(
            object : Callback<DestinationsResponse>{
                override fun onResponse(
                    call: Call<DestinationsResponse>,
                    response: Response<DestinationsResponse>
                ) {
                    if (response.isSuccessful){
                        destinationsLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DestinationsResponse>, t: Throwable) {

                }

            }
        )
        return destinationsLiveData
    }

    fun getDestinationById(id: Int) : LiveData<DestinationResponse> {
        apiService.destinationById(id).enqueue(
            object : Callback<DestinationResponse>{
                override fun onResponse(
                    call: Call<DestinationResponse>,
                    response: Response<DestinationResponse>
                ) {
                    if (response.isSuccessful){
                        destinationLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DestinationResponse>, t: Throwable) {

                }

            }
        )
        return destinationLiveData
    }

}