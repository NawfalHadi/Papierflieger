package com.papierflieger.presentation.ui.home.search.flights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.papierflieger.R
import com.papierflieger.data.local.room.entity.AirportEntity
import com.papierflieger.data.network.response.ticket.DataTicket
import com.papierflieger.data.network.response.ticket.SearchTicketResponse
import com.papierflieger.data.network.response.ticket.TiketBerangkat
import com.papierflieger.data.network.response.ticket.TiketPulang
import com.papierflieger.databinding.FragmentListFlightReturnBinding
import com.papierflieger.presentation.ui.adapter.tickets.ArrivalAdapter
import com.papierflieger.presentation.ui.home.search.SearchActivity
import com.papierflieger.presentation.ui.home.search.bottomsheet.TicketPreviewBottomSheet
import com.papierflieger.wrapper.toDataTicket
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListReturnFragment : Fragment() {

    private lateinit var binding : FragmentListFlightReturnBinding
    private val ticketAdapter : ArrivalAdapter by lazy { ArrivalAdapter() }

    private lateinit var departureChoosed : TiketBerangkat
    private var listTicket : ArrayList<DataTicket> = arrayListOf()

    private lateinit var airportFrom : AirportEntity
    private lateinit var airportTo : AirportEntity

    // data that will passed to passenger data page
    private var ticketsPreview : ArrayList<DataTicket> = arrayListOf()
    private var passengerCounter : Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListFlightReturnBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val responsed = arguments?.getParcelable<SearchTicketResponse>(SearchActivity.RETURN_TICKETS_KEY)

        departureChoosed = arguments?.getParcelable(SearchActivity.DEPARTURE_TICKET_KEY)!!
        listTicket = arguments?.getParcelableArrayList(SearchActivity.TICKETS_KEY)!!

        passengerCounter = arguments?.getInt(SearchActivity.PASSENGER_COUNTER_KEY, 1) ?: 1

        bindingHeader()
        bindingDepartureData(departureChoosed)
        showsRecycler(responsed?.tiketPulang)
    }

    private fun bindingHeader() {
        with(binding){
            tvPassenger.text = StringBuilder(passengerCounter.toString()).append(" ${getString(R.string.text_passenger)}")
        }
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

        ticketAdapter.itemAction(object : ArrivalAdapter.OnArrivalTicketActionCallback{
            override fun ticketClicked(ticket: TiketPulang?) {
                ticketsPreview.clear()
                ticketsPreview.addAll(listTicket)
                ticketsPreview.add(ticket?.toDataTicket()!!)

                val currentDialog = parentFragmentManager.findFragmentByTag(TicketPreviewBottomSheet::class.java.simpleName)
                if (currentDialog == null){
                    TicketPreviewBottomSheet(ticketsPreview,
                        object : TicketPreviewBottomSheet.OnTicketPreviewListener{
                            override fun continueClicked(previews: ArrayList<DataTicket>) {
                                val mBundle = Bundle()
                                mBundle.putParcelableArrayList(SearchActivity.TICKETS_KEY, ticketsPreview)
                                mBundle.putInt(SearchActivity.PASSENGER_COUNTER_KEY, passengerCounter)
                                findNavController().navigate(R.id.action_listReturnFragment_to_passengerFragment, mBundle)
                            }

                        }
                    ).show(
                        parentFragmentManager, TicketPreviewBottomSheet::class.java.simpleName
                    )
                }
            }

        })
    }


}