package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.ChangeDataResponse
import com.papierflieger.data.network.response.airplane.CreateAirplaneResponse
import com.papierflieger.data.network.response.airport.CreateAirportResponse
import com.papierflieger.data.network.response.destination.CreateDestinationResponse
import com.papierflieger.data.network.response.orders.OrdersResponse
import com.papierflieger.data.network.response.ticket.CreateTicketResponse
import com.papierflieger.data.network.response.transaction.TransactionsResponse
import com.papierflieger.data.network.response.user.UsersResponse
import com.papierflieger.data.network.service.ApiAdminService
import com.papierflieger.wrapper.Resource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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
     * Order
     */

    private var ordersResponse : MutableLiveData<Resource<OrdersResponse>> = MutableLiveData()
    private var deleteOrderResponse : MutableLiveData<Resource<ChangeDataResponse>> = MutableLiveData()

    /***
     * Transaction
     */

    private var transactionsResponse : MutableLiveData<Resource<TransactionsResponse>> = MutableLiveData()
    private var deleteTransactionResponse : MutableLiveData<Resource<ChangeDataResponse>> = MutableLiveData()

    /***
     * User
     */

    private var usersResponse : MutableLiveData<Resource<UsersResponse>> = MutableLiveData()
    private var updateUserResponse : MutableLiveData<Resource<ChangeDataResponse>> = MutableLiveData()


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
    ) : LiveData<Resource<CreateDestinationResponse>> {
        val parts = mutableListOf<MultipartBody.Part>()
        for (file in images) {
            val part = MultipartBody.Part.createFormData("images", file.name,
                file.asRequestBody("image/${file.extension}".toMediaTypeOrNull()))
            parts.add(part)
        }
        apiAdminService.createAdminDestination(
            token,
            stringToPart(name),
            parts,
            stringToPart(location),
            stringToPart(description),
            stringToPart(airportId.toString())
        ).enqueue(
            object : Callback<CreateDestinationResponse>{
                override fun onResponse(
                    call: Call<CreateDestinationResponse>,
                    response: Response<CreateDestinationResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        createDestinationResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        createDestinationResponse.value = Resource.Error(Throwable(errorMessage))
                    }
                }
                override fun onFailure(call: Call<CreateDestinationResponse>, t: Throwable) {
                    createDestinationResponse.postValue(Resource.Error(t))
                }
            }
        )
        return createDestinationResponse
    }

    private fun stringToPart(stringData: String): RequestBody {
        return stringData.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun updateDestination(
        idDestination: Int,
        token: String,
        name: String,
        images: List<File>,
        location: String,
        description: String,
        airportId: Int
    ) : LiveData<Resource<ChangeDataResponse>> {
        val parts = mutableListOf<MultipartBody.Part>()
        for (file in images) {
            val part = MultipartBody.Part.createFormData("images", file.name,
                file.asRequestBody("image/${file.extension}".toMediaTypeOrNull()))
            parts.add(part)
        }
        apiAdminService.updateAdminDestination(
            idDestination,
            token,
            stringToPart(name),
            parts,
            stringToPart(location),
            stringToPart(description),
            stringToPart(airportId.toString())
        ).enqueue(
            object : Callback<ChangeDataResponse>{
                override fun onResponse(
                    call: Call<ChangeDataResponse>,
                    response: Response<ChangeDataResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        updateDestinationResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        updateDestinationResponse.value = Resource.Error(Throwable(errorMessage))
                    }
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
                    val body = response.body()
                    if (body != null) {
                        deleteDestinationResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        deleteDestinationResponse.value = Resource.Error(Throwable(errorMessage))
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
                    val body = response.body()
                    if (body != null) {
                        createAirportResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        createAirportResponse.value = Resource.Error(Throwable(errorMessage))
                    }
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
                    val body = response.body()
                    if (body != null) {
                        updateAirportResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        updateAirportResponse.value = Resource.Error(Throwable(errorMessage))
                    }
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
                    val body = response.body()
                    if (body != null) {
                        deleteAirportResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        deleteAirportResponse.value = Resource.Error(Throwable(errorMessage))
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
                    val body = response.body()
                    if (body != null) {
                        createAirplaneResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        createAirplaneResponse.value = Resource.Error(Throwable(errorMessage))
                    }
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
                    val body = response.body()
                    if (body != null) {
                        updateAirplaneResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        updateAirplaneResponse.value = Resource.Error(Throwable(errorMessage))
                    }
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
                    val body = response.body()
                    if (body != null) {
                        deleteAirplaneResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        deleteAirplaneResponse.value = Resource.Error(Throwable(errorMessage))
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
    ) : LiveData<Resource<CreateTicketResponse>> {
        apiAdminService.createAdminTicketTransit(
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
                    val body = response.body()
                    if (body != null) {
                        createTicketResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        createTicketResponse.value = Resource.Error(Throwable(errorMessage))
                    }
                }
                override fun onFailure(call: Call<CreateTicketResponse>, t: Throwable) {
                    createTicketResponse.postValue(Resource.Error(t))
                }
            }
        )
        return createTicketResponse
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
    ) : LiveData<Resource<CreateTicketResponse>> {
        apiAdminService.createAdminTicketDirect(
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
        ).enqueue(
            object : Callback<CreateTicketResponse>{
                override fun onResponse(
                    call: Call<CreateTicketResponse>,
                    response: Response<CreateTicketResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        createTicketResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        createTicketResponse.value = Resource.Error(Throwable(errorMessage))
                    }
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
        totalTransit: Int?,
        transitPoint: Int?,
        transitDuration: String?,
        ticketType: String,
        flightDuration: String,
        arrivalTimeAtTransit: String?,
        departureTimeFromTransit: String?,
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
                    val body = response.body()
                    if (body != null) {
                        updateTicketResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        updateTicketResponse.value = Resource.Error(Throwable(errorMessage))
                    }
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
                    val body = response.body()
                    if (body != null) {
                        deleteTicketResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        deleteTicketResponse.value = Resource.Error(Throwable(errorMessage))
                    }
                }
                override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                    deleteTicketResponse.postValue(Resource.Error(t))
                }
            }
        )
        return deleteTicketResponse
    }

    /***
     * Order
     */

    fun getOrders(token: String) : LiveData<Resource<OrdersResponse>> {
        apiAdminService.getAdminOrders(token).enqueue(
            object : Callback<OrdersResponse>{
                override fun onResponse(
                    call: Call<OrdersResponse>,
                    response: Response<OrdersResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        ordersResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        ordersResponse.value = Resource.Error(Throwable(errorMessage))
                    }
                }

                override fun onFailure(call: Call<OrdersResponse>, t: Throwable) {
                    ordersResponse.postValue(Resource.Error(t))
                }

            }
        )
        return ordersResponse
    }

    fun deleteOrder(idOrder: Int, token: String) : LiveData<Resource<ChangeDataResponse>> {
        apiAdminService.deleteAdminOrder(idOrder, token).enqueue(
            object : Callback<ChangeDataResponse>{
                override fun onResponse(
                    call: Call<ChangeDataResponse>,
                    response: Response<ChangeDataResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        deleteOrderResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        deleteOrderResponse.value = Resource.Error(Throwable(errorMessage))
                    }
                }

                override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                    deleteOrderResponse.postValue(Resource.Error(t))
                }

            }
        )
        return deleteOrderResponse
    }

    /***
     * Transaction
     */

    fun getTransactions(token: String) : LiveData<Resource<TransactionsResponse>> {
        apiAdminService.getAdminTransactions(token).enqueue(
            object : Callback<TransactionsResponse>{
                override fun onResponse(
                    call: Call<TransactionsResponse>,
                    response: Response<TransactionsResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        transactionsResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        transactionsResponse.value = Resource.Error(Throwable(errorMessage))
                    }
                }

                override fun onFailure(call: Call<TransactionsResponse>, t: Throwable) {
                    transactionsResponse.postValue(Resource.Error(t))
                }

            }
        )
        return transactionsResponse
    }

    fun deleteTransaction(idTransaction: Int, token: String) : LiveData<Resource<ChangeDataResponse>> {
        apiAdminService.deleteAdminTransaction(idTransaction, token).enqueue(
            object : Callback<ChangeDataResponse>{
                override fun onResponse(
                    call: Call<ChangeDataResponse>,
                    response: Response<ChangeDataResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        deleteTransactionResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        deleteTransactionResponse.value = Resource.Error(Throwable(errorMessage))
                    }
                }

                override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                    deleteTransactionResponse.postValue(Resource.Error(t))
                }

            }
        )
        return deleteTransactionResponse
    }

    /***
     * Transaction
     */

    fun getUsers(token: String) : LiveData<Resource<UsersResponse>> {
        apiAdminService.getAdminUser(token).enqueue(
            object : Callback<UsersResponse>{
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        usersResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        usersResponse.value = Resource.Error(Throwable(errorMessage))
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    usersResponse.postValue(Resource.Error(t))
                }

            }
        )
        return usersResponse
    }

    fun updateUser(idUser: Int, token: String) : LiveData<Resource<ChangeDataResponse>> {
        apiAdminService.updateAdminUser(idUser, token).enqueue(
            object : Callback<ChangeDataResponse>{
                override fun onResponse(
                    call: Call<ChangeDataResponse>,
                    response: Response<ChangeDataResponse>
                ) {
                    val body = response.body()
                    if (body != null) {
                        updateUserResponse.postValue(Resource.Success(body))
                    } else {
                        val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                        val errorMessage = errorJson?.optString("message")
                        updateUserResponse.value = Resource.Error(Throwable(errorMessage))
                    }
                }

                override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                    updateUserResponse.postValue(Resource.Error(t))
                }

            }
        )
        return updateUserResponse
    }
}