package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.papierflieger.data.network.response.ChangeDataResponse
import com.papierflieger.data.network.response.destination.CreateDestinationResponse
import com.papierflieger.data.network.response.airplane.CreateAirplaneResponse
import com.papierflieger.data.network.response.airport.CreateAirportResponse
import com.papierflieger.data.network.response.ticket.CreateTicketResponse
import com.papierflieger.data.repository.AdminRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel(){

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
    ): LiveData<Resource<CreateDestinationResponse>> {
        return adminRepository.createDestination(
            token, name, images, location, description, airportId
        )
    }

    fun updateDestination(
        idDestination: Int,
        token: String,
        name: String,
        images: List<String>,
        location: String,
        description: String,
        airportId: Int
    ): LiveData<Resource<ChangeDataResponse>> {
        return adminRepository.updateDestination(
            idDestination, token, name, images, location, description, airportId
        )
    }

    fun deleteDestination(
        idDestination: Int,
        token: String
    ) : LiveData<Resource<ChangeDataResponse>> {
        return adminRepository.deleteDestination(idDestination, token)
    }

    /***
     * Airport
     */

    fun createAirport(
        token: String,
        airportName: String,
        city: String,
        cityCode: String
    ): LiveData<Resource<CreateAirportResponse>> {
        return adminRepository.createAirport(token, airportName, city, cityCode)
    }

    fun updateAirport(
        idAirport: Int,
        token: String,
        airportName: String,
        city: String,
        cityCode: String
    ): LiveData<Resource<ChangeDataResponse>> {
        return adminRepository.updateAirport(idAirport, token, airportName, city, cityCode)
    }

    fun deleteAirport(
        idAirport: Int,
        token: String
    ) : LiveData<Resource<ChangeDataResponse>> {
        return adminRepository.deleteAirport(idAirport, token)
    }

    /***
     * Airplane
     */

    fun createAirplane(
        token: String,
        airplaneName: String,
        airplaneCode: String,
        category: String
    ): LiveData<Resource<CreateAirplaneResponse>> {
        return adminRepository.createAirplane(token, airplaneName, airplaneCode, category)
    }

    fun updateAirplane(
        idAirplane: Int,
        token: String,
        airplaneName: String,
        airplaneCode: String,
        category: String
    ): LiveData<Resource<ChangeDataResponse>> {
        return adminRepository.updateAirplane(idAirplane, token, airplaneName, airplaneCode, category)
    }

    fun deleteAirplane(
        idAirplane: Int,
        token: String
    ) : LiveData<Resource<ChangeDataResponse>> {
        return adminRepository.deleteAirplane(idAirplane, token)
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
    ): LiveData<Resource<CreateTicketResponse>> {
        return adminRepository.createTicket(
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
        )
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
    ): LiveData<Resource<ChangeDataResponse>> {
        return adminRepository.updateTicket(
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
        )
    }

    fun deleteTicket(
        idTicket: Int,
        token: String
    ) : LiveData<Resource<ChangeDataResponse>> {
        return adminRepository.deleteTicket(idTicket, token)
    }

}