package com.papierflieger.data.network.service

import com.papierflieger.data.network.response.ChangeDataResponse
import com.papierflieger.data.network.response.CreateDestinationResponse
import com.papierflieger.data.network.response.airplane.CreateAirplaneResponse
import com.papierflieger.data.network.response.airport.CreateAirportResponse
import com.papierflieger.data.network.response.ticket.CreateTicketResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiAdminService {

    /***
     * Destination
     */

    @FormUrlEncoded
    @POST("api/destinations")
    fun createAdminDestination(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("images") images: String,
        @Field("location") location: String,
        @Field("description") description: String,
        @Field("airportId") airportId: Int,
    ) : Call<CreateDestinationResponse>

    @FormUrlEncoded
    @PUT("api/destinations/{idDestination}")
    fun updateAdminDestination(
        @Path("idDestination") idDestination: Int,
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("images") images: String,
        @Field("location") location: String,
        @Field("description") description: String,
        @Field("airportId") airportId: Int,
    ) : Call<ChangeDataResponse>

    @DELETE("api/destinations/{idDestination}")
    fun deleteAdminDestination(
        @Path("idDestination") idDestination: Int,
        @Header("Authorization") token: String,
    ) : Call<ChangeDataResponse>

    /***
     * Airport
     */

    @FormUrlEncoded
    @POST("api/airports")
    fun createAdminAirport(
        @Header("Authorization") token: String,
        @Field("airportName") airportName: String,
        @Field("city") city: String,
        @Field("cityCode") cityCode: String,
    ) : Call<CreateAirportResponse>

    @FormUrlEncoded
    @PUT("api/airports/{idAirport}")
    fun updateAdminAirport(
        @Path("idAirport") idAirport: Int,
        @Header("Authorization") token: String,
        @Field("airportName") airportName: String,
        @Field("city") city: String,
        @Field("cityCode") cityCode: String,
    ) : Call<ChangeDataResponse>

    @DELETE("api/airports/{idAirport}")
    fun deleteAdminAirport(
        @Path("idAirport") idAirport: Int,
        @Header("Authorization") token: String,
    ) : Call<ChangeDataResponse>

    /***
     * Airplane
     */

    @FormUrlEncoded
    @POST("api/airplanes")
    fun createAdminAirplane(
        @Header("Authorization") token: String,
        @Field("airplaneName") airplaneName: String,
        @Field("airplaneCode") airplaneCode: String,
        @Field("class") category: String,
    ) : Call<CreateAirplaneResponse>

    @FormUrlEncoded
    @PUT("api/airplanes/{idAirplane}")
    fun updateAdminAirplane(
        @Path("idAirplane") idAirplane: Int,
        @Header("Authorization") token: String,
        @Field("airplaneName") airplaneName: String,
        @Field("airplaneCode") airplaneCode: String,
        @Field("class") category: String,
    ) : Call<ChangeDataResponse>

    @DELETE("api/airplanes/{idAirplane}")
    fun deleteAdminAirplane(
        @Path("idAirplane") idAirplane: Int,
        @Header("Authorization") token: String,
    ) : Call<ChangeDataResponse>

    /***
     * Ticket
     */

    @FormUrlEncoded
    @POST("api/tickets")
    fun createAdminTicketTransit(
        @Header("Authorization") token: String,
        @Field("ticketNumber") ticketNumber: Int,
        @Field("departureDate") departureDate: String,
        @Field("departureTime") departureTime: String,
        @Field("arrivalDate") arrivalDate: String,
        @Field("arrivalTime") arrivalTime: String,
        @Field("flightFrom") flightFrom: Int,
        @Field("flightTo") flightTo: Int,
        @Field("airplaneId") airplaneId: Int,
        @Field("price") price: Int,
        @Field("totalTransit") totalTransit: Int,
        @Field("transitPoint") transitPoint: Int,
        @Field("transitDuration") transitDuration: String,
        @Field("ticketType") ticketType: String,
        @Field("flightDuration") flightDuration: String,
        @Field("arrivalTimeAtTransit") arrivalTimeAtTransit: String,
        @Field("departureTimeFromTransit") departureTimeFromTransit: String,
    ) : Call<CreateTicketResponse>

    @FormUrlEncoded
    @POST("api/tickets")
    fun createAdminTicketDirect(
        @Header("Authorization") token: String,
        @Field("ticketNumber") ticketNumber: Int,
        @Field("departureDate") departureDate: String,
        @Field("departureTime") departureTime: String,
        @Field("arrivalDate") arrivalDate: String,
        @Field("arrivalTime") arrivalTime: String,
        @Field("flightFrom") flightFrom: Int,
        @Field("flightTo") flightTo: Int,
        @Field("airplaneId") airplaneId: Int,
        @Field("price") price: Int,
        @Field("ticketType") ticketType: String,
        @Field("flightDuration") flightDuration: String,
    ) : Call<CreateTicketResponse>

    @FormUrlEncoded
    @PUT("api/tickets/{idTicket}")
    fun updateAdminTicket(
        @Path("idTicket") idTicket: Int,
        @Header("Authorization") token: String,
        @Field("ticketNumber") ticketNumber: Int,
        @Field("departureDate") departureDate: String,
        @Field("departureTime") departureTime: String,
        @Field("arrivalDate") arrivalDate: String,
        @Field("arrivalTime") arrivalTime: String,
        @Field("flightFrom") flightFrom: Int,
        @Field("flightTo") flightTo: Int,
        @Field("airplaneId") airplaneId: Int,
        @Field("price") price: Int,
        @Field("totalTransit") totalTransit: Int?,
        @Field("transitPoint") transitPoint: Int?,
        @Field("transitDuration") transitDuration: String?,
        @Field("ticketType") ticketType: String,
        @Field("flightDuration") flightDuration: String,
        @Field("arrivalTimeAtTransit") arrivalTimeAtTransit: String?,
        @Field("departureTimeFromTransit") departureTimeFromTransit: String?,
    ) : Call<ChangeDataResponse>

    @DELETE("api/tickets/{idTickets}")
    fun deleteAdminTicket(
        @Path("idTickets") idTickets: Int,
        @Header("Authorization") token: String,
    ) : Call<ChangeDataResponse>

}