package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.airplane.AirplaneResponse
import com.papierflieger.data.network.response.airplane.AirplanesResponse
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirplaneRepository(
    private val apiService: ApiService
){
    private var airplanesResponse : MutableLiveData<Resource<AirplanesResponse>> = MutableLiveData()
    private var airplaneResponse : MutableLiveData<Resource<AirplaneResponse>> = MutableLiveData()

    fun getAirplanes() : LiveData<Resource<AirplanesResponse>> {
        apiService.getAirplanes().enqueue(
            object : Callback<AirplanesResponse>{
                override fun onResponse(
                    call: Call<AirplanesResponse>,
                    response: Response<AirplanesResponse>
                ) {
                    if (response.isSuccessful){
                        airplanesResponse.postValue(Resource.Success(response.body()!!))
                    }
                }
                override fun onFailure(call: Call<AirplanesResponse>, t: Throwable) {
                    airplanesResponse.postValue(Resource.Error(t))

                }
            }
        )
        return airplanesResponse
    }

    fun getAirplaneById(
        idAirport: Int
    ) : LiveData<Resource<AirplaneResponse>> {
        apiService.getAirplaneById(idAirport).enqueue(
            object : Callback<AirplaneResponse>{
                override fun onResponse(
                    call: Call<AirplaneResponse>,
                    response: Response<AirplaneResponse>
                ) {
                    if (response.isSuccessful){
                        airplaneResponse.postValue(Resource.Success(response.body()!!))
                    }
                }
                override fun onFailure(call: Call<AirplaneResponse>, t: Throwable) {
                    airplaneResponse.postValue(Resource.Error(t))

                }
            }
        )
        return airplaneResponse
    }

}