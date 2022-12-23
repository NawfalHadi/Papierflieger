package com.papierflieger.data.network.service

import com.papierflieger.data.network.response.CreateDestinationResponse
import com.papierflieger.data.network.response.UpdateDestinationResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiAdminService {

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
        @Field("images") images: List<String?>?,
        @Field("location") location: String,
        @Field("description") description: String,
        @Field("airportId") airportId: Int,
    ) : Call<UpdateDestinationResponse>


}