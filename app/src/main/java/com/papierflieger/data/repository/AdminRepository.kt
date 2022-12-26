package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.ChangeDataResponse
import com.papierflieger.data.network.response.destination.CreateDestinationResponse
import com.papierflieger.data.network.response.airplane.CreateAirplaneResponse
import com.papierflieger.data.network.response.airport.CreateAirportResponse
import com.papierflieger.data.network.response.ticket.CreateTicketResponse
import com.papierflieger.data.network.service.ApiAdminService
import com.papierflieger.wrapper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminRepository(
    private val apiAdminService: ApiAdminService
) {

    /***
     * Destination
     */

    private var updateDestinationResponse : MutableLiveData<Resource<ChangeDataResponse>> = MutableLiveData()
    private var createDestinationResponse : MutableLiveData<Resource<CreateDestinationResponse>> = MutableLiveData()
    private var deleteDestinationResponse : MutableLiveData<Resource<ChangeDataResponse>> = MutableLiveData()

    /***
     * Airport
     */

    private var updateAirportResponse : MutableLiveData<Resource<ChangeDataResponse>> = MutableLiveData()
    private var createAirportResponse : MutableLiveData<Resource<CreateAirportResponse>> = MutableLiveData()
    private var deleteAirportResponse : MutableLiveData<Resource<ChangeDataResponse>> = MutableLiveData()

    /***
     * Airplane
     */

    private var updateAirplaneResponse : MutableLiveData<Resource<ChangeDataResponse>> = MutableLiveData()
    private var createAirplaneResponse : MutableLiveData<Resource<CreateAirplaneResponse>> = MutableLiveData()
    private var deleteAirplaneResponse : MutableLiveData<Resource<ChangeDataResponse>> = MutableLiveData()

    /***
     * Ticket
     */

    private var updateTicketResponse : MutableLiveData<Resource<ChangeDataResponse>> = MutableLiveData()
    private var createTicketResponse : MutableLiveData<Resource<CreateTicketResponse>> = MutableLiveData()
    private var deleteTicketResponse : MutableLiveData<Resource<ChangeDataResponse>> = MutableLiveData()

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
    ) : LiveData<Resource<CreateDestinationResponse>> {
        apiAdminService.createAdminDestination(token, name, images, location, description, airportId).enqueue(
            object : Callback<CreateDestinationResponse>{
                override fun onResponse(
                    call: Call<CreateDestinationResponse>,
                    response: Response<CreateDestinationResponse>
                ) {
                    createDestinationResponse.postValue(Resource.Success(response.body()!!))
                }
                override fun onFailure(call: Call<CreateDestinationResponse>, t: Throwable) {
                    createDestinationResponse.postValue(Resource.Error(t))
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
    ) : LiveData<Resource<ChangeDataResponse>> {
        apiAdminService.updateAdminDestination(idDestination, token, name, images, location, description, airportId).enqueue(
            object : Callback<ChangeDataResponse>{
                override fun onResponse(
                    call: Call<ChangeDataResponse>,
                    response: Response<ChangeDataResponse>
                ) {
                    updateDestinationResponse.postValue(Resource.Success(response.body()!!))
                }
                override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                    updateDestinationResponse.postValue(Resource.Error(t))
                }
            }
        )

        return updateDestinationResponse
    }

    fun deleteDestination(idDestination: Int, token: String) : LiveData<Resource<ChangeDataResponse>> {
        apiAdminService.deleteAdminDestination(idDestination, token).enqueue(
            object : Callback<ChangeDataResponse>{
                override fun onResponse(
                    call: Call<ChangeDataResponse>,
                    response: Response<ChangeDataResponse>
                ) {
                    if (response.isSuccessful){
                        deleteDestinationResponse.postValue(Resource.Success(response.body()!!))
                    }
                }
                override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                    deleteDestinationResponse.postValue(Resource.Error(t))
                }
            }
        )
        return deleteDestinationResponse
    }

    /***
     * Airport
     */

    fun createAirport(
        token: String,
        airportName: String,
        city: String,
        cityCode: String
    ) : LiveData<Resource<CreateAirportResponse>> {
        apiAdminService.createAdminAirport(token, airportName, city, cityCode).enqueue(
            object : Callback<CreateAirportResponse>{
                override fun onResponse(
                    call: Call<CreateAirportResponse>,
                    response: Response<CreateAirportResponse>
                ) {
                    createAirportResponse.postValue(Resource.Success(response.body()!!))
                }
                override fun onFailure(call: Call<CreateAirportResponse>, t: Throwable) {
                    createAirportResponse.postValue(Resource.Error(t))
                }
            }
        )

        return createAirportResponse
    }

    fun updateAirport(
        idAirport: Int,
        token: String,
        airportName: String,
        city: String,
        cityCode: String
    ) : LiveData<Resource<ChangeDataResponse>> {
        apiAdminService.updateAdminAirport(idAirport, token, airportName, city, cityCode).enqueue(
            object : Callback<ChangeDataResponse>{
                override fun onResponse(
                    call: Call<ChangeDataResponse>,
                    response: Response<ChangeDataResponse>
                ) {
                    updateAirportResponse.postValue(Resource.Success(response.body()!!))
                }
                override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                    updateAirportResponse.postValue(Resource.Error(t))
                }
            }
        )

        return updateAirportResponse
    }

    fun deleteAirport(idAirport: Int, token: String) : LiveData<Resource<ChangeDataResponse>> {
        apiAdminService.deleteAdminAirport(idAirport, token).enqueue(
            object : Callback<ChangeDataResponse>{
                override fun onResponse(
                    call: Call<ChangeDataResponse>,
                    response: Response<ChangeDataResponse>
                ) {
                    if (response.isSuccessful){
                        deleteAirportResponse.postValue(Resource.Success(response.body()!!))
                    }
                }
                override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                    deleteAirportResponse.postValue(Resource.Error(t))
                }
            }
        )
        return deleteAirportResponse
    }

    /***
     * Airplane
     */

    fun createAirplane(
        token: String,
        airplaneName: String,
        airplaneCode: String,
        category: String
    ) : LiveData<Resource<CreateAirplaneResponse>> {
        apiAdminService.createAdminAirplane(token, airplaneName, airplaneCode, category).enqueue(
            object : Callback<CreateAirplaneResponse>{
                override fun onResponse(
                    call: Call<CreateAirplaneResponse>,
                    response: Response<CreateAirplaneResponse>
                ) {
                    createAirplaneResponse.postValue(Resource.Success(response.body()!!))
                }
                override fun onFailure(call: Call<CreateAirplaneResponse>, t: Throwable) {
                    createAirplaneResponse.postValue(Resource.Error(t))
                }
            }
        )

        return createAirplaneResponse
    }

    fun updateAirplane(
        idAirport: Int,
        token: String,
        airplaneName: String,
        airplaneCode: String,
        category: String
    ) : LiveData<Resource<ChangeDataResponse>> {
        apiAdminService.updateAdminAirplane(idAirport, token, airplaneName, airplaneCode, category).enqueue(
            object : Callback<ChangeDataResponse>{
                override fun onResponse(
                    call: Call<ChangeDataResponse>,
                    response: Response<ChangeDataResponse>
                ) {
                    updateAirplaneResponse.postValue(Resource.Success(response.body()!!))
                }
                override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                    updateAirplaneResponse.postValue(Resource.Error(t))
                }
            }
        )

        return updateAirplaneResponse
    }

    fun deleteAirplane(idAirplane: Int, token: String) : LiveData<Resource<ChangeDataResponse>> {
        apiAdminService.deleteAdminAirplane(idAirplane, token).enqueue(
            object : Callback<ChangeDataResponse>{
                override fun onResponse(
                    call: Call<ChangeDataResponse>,
                    response: Response<ChangeDataResponse>
                ) {
                    if (response.isSuccessful){
                        deleteAirplaneResponse.postValue(Resource.Success(response.body()!!))
                    }
                }
                override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                    deleteAirplaneResponse.postValue(Resource.Error(t))
                }
            }
        )
        return deleteAirplaneResponse
    }

    /***
     * Ticket
     */

    fun createTicket(
        token: String,
        ticketNumber: Int,
        departureDate: String,
        departureTime: String,
        arrivalDate: String,
        arrivalTime: String,
        flightFrom: Int,
        flightTo: Int,
        airplaneId: Int,
        price: Int,
        totalTransit: Int,
        transitPoint: Int,
        transitDuration: String,
        ticketType: String,
        flightDuration: String,
        arrivalTimeAtTransit: String,
        departureTimeFromTransit: String,
    ) : LiveData<Resource<CreateTicketResponse>> {
        apiAdminService.createAdminTicket(
            token,
            ticketNumber,
            departureDate,
            departureTime,
            arrivalDate,
            arrivalTime,
            flightFrom,
            flightTo,
            airplaneId,
            price,
            totalTransit,
            transitPoint,
            transitDuration,
            ticketType,
            flightDuration,
            arrivalTimeAtTransit,
            departureTimeFromTransit
        ).enqueue(
            object : Callback<CreateTicketResponse>{
                override fun onResponse(
                    call: Call<CreateTicketResponse>,
                    response: Response<CreateTicketResponse>
                ) {
                    createTicketResponse.postValue(Resource.Success(response.body()!!))
                }
                override fun onFailure(call: Call<CreateTicketResponse>, t: Throwable) {
                    createTicketResponse.postValue(Resource.Error(t))
                }
            }
        )
        return createTicketResponse
    }

    fun updateTicket(
        idTicket: Int,
        token: String,
        ticketNumber: Int,
        departureDate: String,
        departureTime: String,
        arrivalDate: String,
        arrivalTime: String,
        flightFrom: Int,
        flightTo: Int,
        airplaneId: Int,
        price: Int,
        totalTransit: Int,
        transitPoint: Int,
        transitDuration: String,
        ticketType: String,
        flightDuration: String,
        arrivalTimeAtTransit: String,
        departureTimeFromTransit: String,
    ) : LiveData<Resource<ChangeDataResponse>> {
        apiAdminService.updateAdminTicket(
            idTicket,
            token,
            ticketNumber,
            departureDate,
            departureTime,
            arrivalDate,
            arrivalTime,
            flightFrom,
            flightTo,
            airplaneId,
            price,
            totalTransit,
            transitPoint,
            transitDuration,
            ticketType,
            flightDuration,
            arrivalTimeAtTransit,
            departureTimeFromTransit
        ).enqueue(
            object : Callback<ChangeDataResponse>{
                override fun onResponse(
                    call: Call<ChangeDataResponse>,
                    response: Response<ChangeDataResponse>
                ) {
                    updateTicketResponse.postValue(Resource.Success(response.body()!!))
                }
                override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                    updateTicketResponse.postValue(Resource.Error(t))
                }
            }
        )
        return updateTicketResponse
    }

    fun deleteTicket(idTicket: Int, token: String) : LiveData<Resource<ChangeDataResponse>> {
        apiAdminService.deleteAdminTicket(idTicket, token).enqueue(
            object : Callback<ChangeDataResponse>{
                override fun onResponse(
                    call: Call<ChangeDataResponse>,
                    response: Response<ChangeDataResponse>
                ) {
                    if (response.isSuccessful){
                        deleteTicketResponse.postValue(Resource.Success(response.body()!!))
                    }
                }
                override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                    deleteTicketResponse.postValue(Resource.Error(t))
                }
            }
        )
        return deleteTicketResponse
    }

}