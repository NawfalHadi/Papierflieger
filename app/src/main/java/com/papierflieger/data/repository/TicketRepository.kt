package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.ticket.ListTicketResponse
import com.papierflieger.data.network.response.ticket.SearchTicketResponse
import com.papierflieger.data.network.response.ticket.TicketResponse
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketRepository(
   private val apiService : ApiService
) {

    private var searchTicketResponse : MutableLiveData<Resource<SearchTicketResponse>> = MutableLiveData()
    private var ticketsResponse : MutableLiveData<Resource<ListTicketResponse>> = MutableLiveData()
    private var ticketResponse : MutableLiveData<Resource<TicketResponse>> = MutableLiveData()

    fun searchTicket(
        idFrom: Int, idTo: Int, departure: String, arrival: String? = null
    ) : LiveData<Resource<SearchTicketResponse>> {
        apiService.searchTickets(
            idFrom,
            idTo,
            departure,
            arrival
        ).enqueue(object : Callback<SearchTicketResponse>{
            override fun onResponse(
                call: Call<SearchTicketResponse>,
                response: Response<SearchTicketResponse>
            ) {
                val body = response.body()
                if (body != null) {
                    searchTicketResponse.value = Resource.Success(body)
                } else {
                    val errorJson = response.errorBody()?.string()?.let { JSONObject(it) }
                    val errorMessage = errorJson?.optString("message")
                    searchTicketResponse.value = Resource.Error(Throwable(errorMessage))
                }
            }
            override fun onFailure(call: Call<SearchTicketResponse>, t: Throwable) {
                searchTicketResponse.postValue(Resource.Error(t))
            }
        })
        return searchTicketResponse
    }

    fun getTickets() : LiveData<Resource<ListTicketResponse>> {
        apiService.getTickets().enqueue(object : Callback<ListTicketResponse>{
            override fun onResponse(
                call: Call<ListTicketResponse>,
                response: Response<ListTicketResponse>
            ) {
                if(response.isSuccessful){
                    ticketsResponse.postValue(Resource.Success(response.body()!!))
                }
            }
            override fun onFailure(call: Call<ListTicketResponse>, t: Throwable) {
                ticketsResponse.postValue(Resource.Error(t))
            }
        })
        return ticketsResponse
    }

    fun getTicketById(idTicket: Int) : LiveData<Resource<TicketResponse>> {
        apiService.getTicketById(idTicket).enqueue(object : Callback<TicketResponse>{
            override fun onResponse(
                call: Call<TicketResponse>,
                response: Response<TicketResponse>
            ) {
                if(response.isSuccessful){
                    ticketResponse.postValue(Resource.Success(response.body()!!))
                }
            }
            override fun onFailure(call: Call<TicketResponse>, t: Throwable) {
                ticketResponse.postValue(Resource.Error(t))
            }
        })
        return ticketResponse
    }

}