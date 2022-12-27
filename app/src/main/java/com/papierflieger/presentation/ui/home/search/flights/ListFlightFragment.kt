package com.papierflieger.presentation.ui.home.search.flights

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.papierflieger.R
import com.papierflieger.data.local.room.entity.AirportEntity
import com.papierflieger.data.network.response.ticket.DataTicket
import com.papierflieger.data.network.response.ticket.SearchTicketResponse
import com.papierflieger.data.network.response.ticket.TiketBerangkat
import com.papierflieger.databinding.FragmentListFlightBinding
import com.papierflieger.presentation.bussiness.TicketViewModel
import com.papierflieger.presentation.ui.adapter.tickets.DepartureAdapter
import com.papierflieger.presentation.ui.home.search.SearchActivity
import com.papierflieger.presentation.ui.home.search.bottomsheet.TicketPreviewBottomSheet
import com.papierflieger.wrapper.Resource
import com.papierflieger.wrapper.toDataTicket
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class ListFlightFragment : Fragment() {

    private lateinit var binding : FragmentListFlightBinding
    private val ticketViewModel : TicketViewModel by viewModels()
    private val ticketAdapter : DepartureAdapter by lazy { DepartureAdapter() }

    private lateinit var responsed : SearchTicketResponse
    private lateinit var departureChoose : TiketBerangkat

    private val ticketsPreview : ArrayList<DataTicket> = arrayListOf()

    // Data that will be used for hitting API
    private lateinit var airportFrom : AirportEntity
    private lateinit var airportTo : AirportEntity
    private lateinit var dateDeparture : String
    private var dateReturn : String? = null

    // Data that needed to bringing until finish
    private var passengerCounter : Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListFlightBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataInit()
        searchTicket()

        bindingHeader()
    }

    private fun bindingHeader() {
        with(binding){
            tvPassenger.text = StringBuilder(passengerCounter.toString()).append(" ${getString(R.string.text_passenger)}")
        }
    }

    private fun dataInit() {
        airportFrom = arguments?.getParcelable(SearchActivity.AIRPORT_FROM_KEY)!!
        airportTo = arguments?.getParcelable(SearchActivity.AIRPORT_TO_KEY)!!
        dateDeparture = arguments?.getString(SearchActivity.DATE_DEPARTURE_KEY).toString()
        dateReturn = arguments?.getString(SearchActivity.DATE_RETURN_KEY).toString()

        passengerCounter = arguments?.getInt(SearchActivity.PASSENGER_COUNTER_KEY, 1) ?: 1
    }

    private fun searchTicket() {
        ticketViewModel.getSearchTicket(
            airportFrom.airportId,
            airportTo.airportId,
            dateDeparture,
            dateReturn
        ).observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> TODO()
                is Resource.Empty -> TODO()
                is Resource.Error -> TODO()
                is Resource.Success -> {
                    responsed = it.payload!!
                    showsRecycler(it.payload.tiketBerangkat)
                }
            }
        }
    }

    private fun showsRecycler(tickets: List<TiketBerangkat?>?) {
        with(binding){
            with(rvTicket){
                ticketAdapter.setItem(tickets!!)
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.VERTICAL, false
                )
                adapter = ticketAdapter

                ticketAdapter.itemAction(object : DepartureAdapter.OnTicketActionCallback{
                    override fun ticketClicked(ticket : TiketBerangkat?) {
                        departureChoose = ticket!!
                        ticketsPreview.clear()
                        ticketsPreview.add(ticket.toDataTicket())

                        if (responsed.tiketPulang.isNullOrEmpty()){
                            val currentDialog = parentFragmentManager.findFragmentByTag(TicketPreviewBottomSheet::class.java.simpleName)
                            if (currentDialog == null){
                                TicketPreviewBottomSheet(ticketsPreview,
                                    object : TicketPreviewBottomSheet.OnTicketPreviewListener{
                                        override fun continueClicked(previews: ArrayList<DataTicket>) {
                                            val mBundle = Bundle()
                                            mBundle.putParcelableArrayList(SearchActivity.TICKETS_KEY, ticketsPreview)
                                            mBundle.putInt(SearchActivity.PASSENGER_COUNTER_KEY, passengerCounter)
                                            findNavController().navigate(R.id.action_listFlightFragment_to_passengerFragment, mBundle)
                                        }
                                    }
                                ).show(
                                    parentFragmentManager, TicketPreviewBottomSheet::class.java.simpleName
                                )
                            }
                        } else {
                            val mBundle = Bundle()
                            mBundle.putParcelableArrayList(SearchActivity.TICKETS_KEY, ticketsPreview)
                            mBundle.putParcelable(SearchActivity.DEPARTURE_TICKET_KEY, departureChoose)
                            mBundle.putParcelable(SearchActivity.RETURN_TICKETS_KEY, responsed)
                            mBundle.putInt(SearchActivity.PASSENGER_COUNTER_KEY, passengerCounter)
                            findNavController().navigate(R.id.action_listFlightFragment_to_listReturnFragment, mBundle)
                        }
                    }

                })
            }
        }
    }
}