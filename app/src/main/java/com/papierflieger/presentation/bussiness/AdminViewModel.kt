package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.papierflieger.data.network.response.ChangeDataResponse
import com.papierflieger.data.network.response.airplane.CreateAirplaneResponse
import com.papierflieger.data.network.response.airport.CreateAirportResponse
import com.papierflieger.data.network.response.destination.CreateDestinationResponse
import com.papierflieger.data.network.response.orders.OrdersResponse
import com.papierflieger.data.network.response.ticket.CreateTicketResponse
import com.papierflieger.data.network.response.transaction.TransactionsResponse
import com.papierflieger.data.network.response.user.UsersResponse
import com.papierflieger.data.repository.AdminRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
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
        images: List<File>,
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
        images: List<File>,
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

    fun createTicketTransit(
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
        return adminRepository.createTicketTransit(
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

    fun createTicketDirect(
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
        ticketType: String,
        flightDuration: String
    ): LiveData<Resource<CreateTicketResponse>> {
        return adminRepository.createTicketDirect(
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
            ticketType,
            flightDuration
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
        totalTransit: Int?,
        transitPoint: Int?,
        transitDuration: String?,
        ticketType: String,
        flightDuration: String,
        arrivalTimeAtTransit: String?,
        departureTimeFromTransit: String?,
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

    /***
     * Order
     */

    fun getOrders(token: String) : LiveData<Resource<OrdersResponse>>{
        return adminRepository.getOrders(token)
    }

    fun deleteOrder(
        idOrder: Int,
        token: String
    ) : LiveData<Resource<ChangeDataResponse>> {
        return adminRepository.deleteOrder(idOrder, token)
    }

    /***
     * Transaction
     */

    fun getTransactions(token: String) : LiveData<Resource<TransactionsResponse>>{
        return adminRepository.getTransactions(token)
    }

    fun deleteTransaction(
        idTransaction: Int,
        token: String
    ) : LiveData<Resource<ChangeDataResponse>> {
        return adminRepository.deleteTransaction(idTransaction, token)
    }

    /***
     * User
     */

    fun getUsers(token: String) : LiveData<Resource<UsersResponse>>{
        return adminRepository.getUsers(token)
    }

    fun updateUser(
        idUser: Int,
        token: String
    ) : LiveData<Resource<ChangeDataResponse>> {
        return adminRepository.updateUser(idUser, token)
    }

}