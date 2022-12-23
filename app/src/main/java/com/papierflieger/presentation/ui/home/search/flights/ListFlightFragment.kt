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
import com.papierflieger.data.network.response.ticket.SearchTicketResponse
import com.papierflieger.data.network.response.ticket.TiketBerangkat
import com.papierflieger.databinding.FragmentListFlightBinding
import com.papierflieger.presentation.bussiness.TicketViewModel
import com.papierflieger.presentation.ui.adapter.tickets.DepartureAdapter
import com.papierflieger.presentation.ui.home.search.SearchActivity
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFlightFragment : Fragment() {

    private lateinit var binding : FragmentListFlightBinding
    private val ticketViewModel : TicketViewModel by viewModels()
    private val ticketAdapter : DepartureAdapter by lazy { DepartureAdapter() }

    private lateinit var responsed : SearchTicketResponse
    private lateinit var departureChoose : TiketBerangkat


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

        searchTicket()
    }

    private fun searchTicket() {
        ticketViewModel.getSearchTicket(from, to, date, date).observe(viewLifecycleOwner){
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

                        if (responsed.tiketPulang.isNullOrEmpty()){
                            //Buat ticket list buat nantinya ditampilkan dalam
                            // bottom sheet dialog

                            //showsBottomDialog(ticket)
                        } else {
                            val mBundle = Bundle()
                            mBundle.putParcelable(SearchActivity.DEPARTURE_TICKET_KEY, departureChoose)
                            mBundle.putParcelable(SearchActivity.RETURN_TICKETS_KEY, responsed)
                            findNavController().navigate(R.id.action_listFlightFragment_to_listReturnFragment, mBundle)
                        }
                    }

                })
            }
        }
    }

    companion object {
        // temporary, delete afterward
        const val from = 40
        const val to = 12
        const val date = "2023-01-01"
    }
}