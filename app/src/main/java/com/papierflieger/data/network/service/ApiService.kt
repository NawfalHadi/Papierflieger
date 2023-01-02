package com.papierflieger.data.network.service
import com.papierflieger.data.local.model.OrderDomestic
import com.papierflieger.data.local.model.OrderInternational
import com.papierflieger.data.local.model.PassengerDomestic
import com.papierflieger.data.local.model.PassengerInternational
import com.papierflieger.data.network.response.ChangeDataResponse
import com.papierflieger.data.network.response.airplane.AirplaneResponse
import com.papierflieger.data.network.response.airplane.AirplanesResponse
import com.papierflieger.data.network.response.airport.AirportResponse
import com.papierflieger.data.network.response.airport.AirportsResponse
import com.papierflieger.data.network.response.auth.LoginResponse
import com.papierflieger.data.network.response.auth.RegisterResponse
import com.papierflieger.data.network.response.destination.DestinationResponse
import com.papierflieger.data.network.response.destination.DestinationsResponse
import com.papierflieger.data.network.response.notification.CreateNotificationResponse
import com.papierflieger.data.network.response.notification.NotificationsResponse
import com.papierflieger.data.network.response.orders.OrderDetailResponse
import com.papierflieger.data.network.response.orders.OrderResponse
import com.papierflieger.data.network.response.ticket.ListTicketResponse
import com.papierflieger.data.network.response.ticket.SearchTicketResponse
import com.papierflieger.data.network.response.ticket.TicketResponse
import com.papierflieger.data.network.response.transaction.HistoriesResponse
import com.papierflieger.data.network.response.transaction.TransactionsResponse
import com.papierflieger.data.network.response.user.UpdateUserResponse
import com.papierflieger.data.network.response.user.UserResponse
import com.papierflieger.data.network.response.wishlist.CreateWishlistResponse
import com.papierflieger.data.network.response.wishlist.WishlistResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /***
     * Authentication API
     * ***/

    @FormUrlEncoded
    @POST("api/auth/register")
    fun register(
        @Field("username") username: String,
        @Field("fullName") fullName: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("api/auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    /***
     * API Authorization Token Needed
     * ***/

    @GET("api/auth/profile/")
    fun userProfile(
        @Header("Authorization") token: String
    ) : Call<UserResponse>

    @GET("api/histories")
    fun getHistoriesUser(
        @Header("Authorization") token: String
    ) : Call<HistoriesResponse>

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
     * Wishlist
     */

    @GET("api/wishlist")
    fun getWishlist(
        @Header("Authorization") token: String,
    ) : Call<WishlistResponse>

    @FormUrlEncoded
    @POST("api/wishlist")
    fun createWishlist(
        @Header("Authorization") token: String,
        @Field("destinationId") destinationId: Int,
    ) : Call<CreateWishlistResponse>

    @DELETE("api/wishlist/{destinationId}")
    fun deleteWishlist(
        @Header("Authorization") token: String,
        @Path("destinationId") destinationId: Int,
    ) : Call<ChangeDataResponse>

    /***
     * Wishlist
     */

    @GET("api/notifications")
    fun getNotifications(
        @Header("Authorization") token: String,
    ) : Call<NotificationsResponse>

    @FormUrlEncoded
    @POST("api/notifications")
    fun createNotification(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("message") message: String,
    ) : Call<CreateNotificationResponse>

    @PUT("api/notifications")
    fun updateNotification(
        @Header("Authorization") token: String,
    ) : Call<ChangeDataResponse>

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
    ) : Call<TicketResponse>

    /***
     * Airports API
     */

    @GET("api/airports")
    fun getAirports() : Call<AirportsResponse>

    @GET("api/airports/{idAirport}")
    fun getAirportById(
        @Path("idAirport") idAirport: Int
    ) : Call<AirportResponse>

    /***
     * Airplanes API
     */

    @GET("api/airplanes")
    fun getAirplanes() : Call<AirplanesResponse>

    @GET("api/airplanes/{idAirplane}")
    fun getAirplaneById(
        @Path("idAirplane") idAirplane: Int
    ) : Call<AirplaneResponse>

    /**
     * Order API
     */

    @GET("api/orders/{idOrder}")
    fun getOrderById(
        @Path("idOrder") idOrder: Int
    ) : Call<OrderDetailResponse>

    @POST("api/orders")
    fun continuePaymentDomestic(
        @Header("Authorization") token: String,
        @Body orderDomestic: OrderDomestic
    ) : Call<OrderResponse>

    @POST("api/orders")
    fun continuePaymentInternational(
        @Header("Authorization") token: String,
        @Body orderInternational: OrderInternational
    ) : Call<OrderResponse>

    /**
     * Transaction API
     */

    @FormUrlEncoded
    @PUT("/api/transactions")
    fun confirmPaymentMethod(
        @Header("Authorization") token: String,
        @Field("bankName") bankName : String,
        @Field("accountName") accountName : String,
        @Field("accountNumber") accountNumber : Int,
        @Field("tokenTransaction") tokenTransaction : String
    ) : Call<TransactionsResponse>
}