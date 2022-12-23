package com.papierflieger.data.network.service
import com.papierflieger.data.network.response.*
import com.papierflieger.data.network.response.airport.AirportResponse
import com.papierflieger.data.network.response.airport.AirportsResponse
import com.papierflieger.data.network.response.auth.LoginResponse
import com.papierflieger.data.network.response.auth.RegisterResponse
import com.papierflieger.data.network.response.destination.DestinationResponse
import com.papierflieger.data.network.response.destination.DestinationsResponse
import com.papierflieger.data.network.response.ticket.ListTicketResponse
import com.papierflieger.data.network.response.ticket.SearchTicketResponse
import com.papierflieger.data.network.response.user.UpdateUserResponse
import com.papierflieger.data.network.response.user.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    /***
     * Authentication API
     * ***/

    @FormUrlEncoded
    @POST("api/auth/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("fullName") fullName: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ) : RegisterResponse

    @FormUrlEncoded
    @POST("api/auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

    /***
     * API Authorization Token Needed
     * ***/

    @GET("api/auth/profile/")
    fun userProfile(
        @Header("Authorization") token: String
    ) : Call<UserResponse>

    @FormUrlEncoded
    @PUT("api/auth/update-profile/")
    fun updatePersonalProfile(
        @Header("Authorization") token: String,
        @Field("title") title: String,
        @Field("username") username: String,
        @Field("fullName") fullName: String,
        @Field("birthdate") birthdate: String,
        @Field("nationality") nationality: String
    ) : Call<UpdateUserResponse>

    @FormUrlEncoded
    @PUT("api/auth/update-profile/")
    fun updateAddressProfile(
        @Header("Authorization") token: String,
        @Field("country") country: String,
        @Field("province") province: String,
        @Field("regency") regency: String,
    ) : Call<UpdateUserResponse>

    /***
     * Destination
     */

    @GET("api/destinations")
    fun destinations() : Call<DestinationsResponse>

    @GET("api/destinations/{idDestination}")
    fun destinationById(
        @Path("idDestination") idDestination: Int
    ) : Call<DestinationResponse>

    /***
     * Ticket API
     */

    @GET("api/search-tickets")
    fun searchTickets(
        @Query("flightFrom") from: Int,
        @Query("flightTo") to: Int,
        @Query("departureDate") departureDate : String,
        @Query("returnDate") returnDate : String?,
    ) : Call<SearchTicketResponse>

    @GET("api/tickets/")
    fun getTickets() : Call<ListTicketResponse>

    @GET("api/tickets/{idTicket}")
    fun getTicketById(
        @Path("idTicket") idTicket: Int
    ) : Call<DataTicket>

    /***
     * Airports API
     */

    @GET("api/airports")
    fun getAirports() : Call<AirportsResponse>

    @GET("api/airports/{idAirport}")
    fun getAirportById(
        @Path("idAirport") idAirport: Int
    ) : Call<AirportResponse>

}