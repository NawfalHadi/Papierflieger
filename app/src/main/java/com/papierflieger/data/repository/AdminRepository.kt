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

class AdminRepository(
    private val apiAdminService: ApiAdminService
) {
    private var updateDestinationResponse : MutableLiveData<Resource<UpdateDestinationResponse>> = MutableLiveData()
    private var createDestinationResponse : MutableLiveData<CreateDestinationResponse> = MutableLiveData()

    fun createDestination(
        token: String,
        name: String,
        images: String,
        location: String,
        description: String,
        airportId: Int
    ) : LiveData<CreateDestinationResponse>{
        apiAdminService.createAdminDestination(token, name, images, location, description, airportId).enqueue(
            object : Callback<CreateDestinationResponse>{
                override fun onResponse(
                    call: Call<CreateDestinationResponse>,
                    response: Response<CreateDestinationResponse>
                ) {
                    createDestinationResponse.postValue(response.body())
                }

                override fun onFailure(call: Call<CreateDestinationResponse>, t: Throwable) {

                }
            }
        )

        return createDestinationResponse
    }

    fun updateDestination(
        idDestination: Int,
        token: String,
        name: String,
        images: List<String>,
        location: String,
        description: String,
        airportId: Int
    ) : LiveData<Resource<UpdateDestinationResponse>>{
        apiAdminService.updateAdminDestination(idDestination, token, name, images, location, description, airportId).enqueue(
            object : Callback<UpdateDestinationResponse>{
                override fun onResponse(
                    call: Call<UpdateDestinationResponse>,
                    response: Response<UpdateDestinationResponse>
                ) {
                    updateDestinationResponse.postValue(Resource.Success(response.body()!!))
                }

                override fun onFailure(call: Call<UpdateDestinationResponse>, t: Throwable) {
                    updateDestinationResponse.postValue(Resource.Error(t))
                }
            }
        )

        return updateDestinationResponse
    }
}