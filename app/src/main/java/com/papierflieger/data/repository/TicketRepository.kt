package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.ticket.SearchTicketResponse
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketRepository(
   private val apiService : ApiService
) {

    private var searchTicketResponse : MutableLiveData<Resource<SearchTicketResponse>> = MutableLiveData()

    fun searchTicket(
        idFrom: Int, idTo: Int, departure: String, arrival: String? = null
    ) : LiveData<Resource<SearchTicketResponse>> {
        apiService.searchTickets(
            from = idFrom,
            to = idTo,
            departureDate = departure,
            returnDate = arrival
        ).enqueue(object : Callback<SearchTicketResponse>{
            override fun onResponse(
                call: Call<SearchTicketResponse>,
                response: Response<SearchTicketResponse>
            ) {
                if(response.isSuccessful){
                    searchTicketResponse.postValue(Resource.Success(response.body()!!))
                }
            }

            override fun onFailure(call: Call<SearchTicketResponse>, t: Throwable) {
                searchTicketResponse.postValue(Resource.Error(t))
            }

        })

        return searchTicketResponse
    }

}