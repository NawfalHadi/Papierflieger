package com.papierflieger.data.network.service
import com.papierflieger.data.network.response.*
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
     * Destionation
     */

    @GET("api/destinations")
    fun destionations() : Call<DestinationsResponse>




}