package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.CreateDestinationResponse
import com.papierflieger.data.network.response.destination.DeleteDestinationResponse
import com.papierflieger.data.network.response.destination.UpdateDestinationResponse
import com.papierflieger.data.network.service.ApiAdminService
import com.papierflieger.wrapper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminRepository(
    private val apiAdminService: ApiAdminService
) {
    private var updateDestinationResponse : MutableLiveData<Resource<UpdateDestinationResponse>> = MutableLiveData()
    private var createDestinationResponse : MutableLiveData<CreateDestinationResponse> = MutableLiveData()
    private var deleteDestinationResponse : MutableLiveData<DeleteDestinationResponse> = MutableLiveData()




    /***
     * Destination
     */

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

    fun deleteDestination(idDestination: Int, token: String) : LiveData<DeleteDestinationResponse> {
        apiAdminService.deleteAdminDestination(idDestination, token).enqueue(
            object : Callback<DeleteDestinationResponse>{
                override fun onResponse(
                    call: Call<DeleteDestinationResponse>,
                    response: Response<DeleteDestinationResponse>
                ) {
                    if (response.isSuccessful){
                        deleteDestinationResponse.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DeleteDestinationResponse>, t: Throwable) {

                }

            }
        )
        return deleteDestinationResponse
    }

    /***
     * Airport
     */
}