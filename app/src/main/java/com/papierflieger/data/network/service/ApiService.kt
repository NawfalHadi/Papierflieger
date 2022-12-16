package com.papierflieger.data.network.service
import com.papierflieger.data.network.response.DestinationsResponse
import com.papierflieger.data.network.response.LoginResponse
import com.papierflieger.data.network.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

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
     * Destionation
     */

    @GET("api/destinations")
    fun destionations() : Call<DestinationsResponse>




}