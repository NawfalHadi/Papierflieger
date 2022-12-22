package com.papierflieger.presentation.ui.home.search.flights

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.papierflieger.data.network.response.ticket.SearchTicketResponse
import com.papierflieger.data.network.response.ticket.TiketBerangkat
import com.papierflieger.data.network.response.ticket.TiketPulang
import com.papierflieger.databinding.FragmentListFlightReturnBinding
import com.papierflieger.presentation.ui.adapter.tickets.ArrivalAdapter
import com.papierflieger.presentation.ui.home.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListReturnFragment : Fragment() {

    private lateinit var binding : FragmentListFlightReturnBinding
    private val ticketAdapter : ArrivalAdapter by lazy { ArrivalAdapter() }

    private lateinit var departureChoosed : TiketBerangkat

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListFlightReturnBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val responsed = arguments?.getParcelable<SearchTicketResponse>(SearchActivity.RETURN_TICKETS_KEY)
        Log.e("Responsed", responsed?.tiketPulang.toString())
        departureChoosed = arguments?.getParcelable(SearchActivity.DEPARTURE_TICKET_KEY)!!

        bindingDepartureData(departureChoosed)
        showsRecycler(responsed?.tiketPulang)
    }

    private fun bindingDepartureData(ticket: TiketBerangkat) {
        with(binding){
            tvDepartureOrigin.text = ticket.flightFrom.toString()
            tvArrivalOrigin.text = ticket.flightTo.toString()

            tvDateOrigin.text = ticket.departureDate
            tvTimeOrigin.text = ticket.departureTime.toString()
            tvPrice.text = ticket.price.toString() // Formatting To Rupiah (not yet)
        }
    }

    private fun showsRecycler(tickets: List<TiketPulang?>?) {
        with(binding.rvTickets){
            ticketAdapter.setItem(tickets!!)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            adapter = ticketAdapter
        }
    }
}