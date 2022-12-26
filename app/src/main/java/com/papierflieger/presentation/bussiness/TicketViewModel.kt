package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.papierflieger.data.network.response.ticket.ListTicketResponse
import com.papierflieger.data.network.response.ticket.SearchTicketResponse
import com.papierflieger.data.network.response.ticket.TicketResponse
import com.papierflieger.data.repository.TicketRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    fun getSearchTicket(
        fromId: Int, toId: Int, departureDate: String, arrivalDate: String? = null
    ) : LiveData<Resource<SearchTicketResponse>> {
        return ticketRepository.searchTicket(
            fromId, toId, departureDate, arrivalDate
        )
    }

    fun getTickets() : LiveData<Resource<ListTicketResponse>> {
        return ticketRepository.getTickets()
    }

    fun getTicketById(idTicket: Int) : LiveData<Resource<TicketResponse>> {
        return ticketRepository.getTicketById(idTicket)
    }

}