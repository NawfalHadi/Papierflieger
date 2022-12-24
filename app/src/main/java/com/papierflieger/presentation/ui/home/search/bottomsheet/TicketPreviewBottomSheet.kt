package com.papierflieger.presentation.ui.home.search.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.papierflieger.data.network.response.ticket.DataTicket
import com.papierflieger.databinding.BottomSheetTicketPreviewBinding
import com.papierflieger.presentation.ui.adapter.tickets.TicketsAdapter

class TicketPreviewBottomSheet(
    private val tickets : List<DataTicket>
) : BottomSheetDialogFragment() {

    private lateinit var binding : BottomSheetTicketPreviewBinding

    private val ticketAdapter : TicketsAdapter by lazy { TicketsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetTicketPreviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener {
            TODO()
        }
        showsTickets()
    }

    private fun showsTickets() {
        with(binding.rvProduct){
            ticketAdapter.setItem(tickets)
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.HORIZONTAL, false
            )

            adapter = ticketAdapter

        }
    }
}