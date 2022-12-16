package com.papierflieger.data.network.service
import com.papierflieger.data.network.response.DestinationsResponse
import com.papierflieger.data.network.response.LoginResponse
import com.papierflieger.data.network.response.RegisterResponse
import com.papierflieger.data.network.response.UserResponse
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
     * Authroization API
     * ***/

    @GET("api/auth/profile/")
    fun userProfile() : Call<UserResponse>


    /***
     * Destionation
     */

    @GET("api/destinations")
    fun destionations() : Call<DestinationsResponse>




}