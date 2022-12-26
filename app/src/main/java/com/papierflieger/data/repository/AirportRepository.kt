package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.local.room.dao.AirportDao
import com.papierflieger.data.local.room.entity.AirportEntity
import com.papierflieger.data.network.response.airport.Airport
import com.papierflieger.data.network.response.airport.AirportResponse
import com.papierflieger.data.network.response.airport.AirportsResponse
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirportRepository(
    private val apiService: ApiService,
    private val daoAirport: AirportDao,
){
    private var airportsResponse : MutableLiveData<Resource<AirportsResponse>> = MutableLiveData()
    private var airportResponse : MutableLiveData<Resource<AirportResponse>> = MutableLiveData()

    fun syncAirportData(
        status : Boolean
    ) : LiveData<Resource<AirportsResponse>> {
        if (status){
            apiService.getAirports().enqueue(
                object : Callback<AirportsResponse>{
                    override fun onResponse(
                        call: Call<AirportsResponse>,
                        response: Response<AirportsResponse>
                    ) {
                        if (response.isSuccessful){
                            airportsResponse.postValue(Resource.Success(response.body()!!))
                        }
                    }
                    override fun onFailure(call: Call<AirportsResponse>, t: Throwable) {
                        airportsResponse.postValue(Resource.Error(t))

                    }
                }
            )
        }
        return airportsResponse
    }

    // By API

    fun getAirports() : LiveData<Resource<AirportsResponse>> {
        apiService.getAirports().enqueue(
            object : Callback<AirportsResponse>{
                override fun onResponse(
                    call: Call<AirportsResponse>,
                    response: Response<AirportsResponse>
                ) {
                    if (response.isSuccessful){
                        airportsResponse.postValue(Resource.Success(response.body()!!))
                    }
                }
                override fun onFailure(call: Call<AirportsResponse>, t: Throwable) {
                    airportsResponse.postValue(Resource.Error(t))

                }
            }
        )
        return airportsResponse
    }

    fun getAirportById(
        idAirport: Int
    ) : LiveData<Resource<AirportResponse>> {
        apiService.getAirportById(idAirport).enqueue(
            object : Callback<AirportResponse>{
                override fun onResponse(
                    call: Call<AirportResponse>,
                    response: Response<AirportResponse>
                ) {
                    if (response.isSuccessful){
                        airportResponse.postValue(Resource.Success(response.body()!!))
                    }
                }
                override fun onFailure(call: Call<AirportResponse>, t: Throwable) {
                    airportResponse.postValue(Resource.Error(t))

                }
            }
        )
        return airportResponse
    }

    // By ROOM

    fun getOfflineAirports() : Resource<List<AirportEntity>>{
        return Resource.Success(daoAirport.showsAirport())
    }

    // To ROOM

    suspend fun offlineAirportData(airport: AirportEntity){
        daoAirport.addAirport(airport)
    }

    fun selectAirport(id: Int){
        daoAirport.selectAirport(id)
    }


}