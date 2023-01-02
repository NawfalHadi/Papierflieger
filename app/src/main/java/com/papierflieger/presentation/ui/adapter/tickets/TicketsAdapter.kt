package com.papierflieger.presentation.ui.adapter.tickets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.ticket.DataTicket
import com.papierflieger.databinding.ItemFlightPassengerChooseBinding
import com.papierflieger.wrapper.convertDateFormat
import com.papierflieger.wrapper.convertTimeFormat

class TicketsAdapter : RecyclerView.Adapter<TicketsAdapter.TicketViewHolder>(){
    private var diffCallback = object : DiffUtil.ItemCallback<DataTicket>(){
        override fun areItemsTheSame(oldItem: DataTicket, newItem: DataTicket): Boolean {
            return oldItem.ticketNumber == newItem.ticketNumber
        }

        override fun areContentsTheSame(oldItem: DataTicket, newItem: DataTicket): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value : List<DataTicket>) = differ.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketViewHolder {
        val binding = ItemFlightPassengerChooseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = differ.currentList[position]
        holder.bindingData(ticket)
    }

    override fun getItemCount(): Int = differ.currentList.size

    class TicketViewHolder(
        private val binding : ItemFlightPassengerChooseBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bindingData(ticket: DataTicket) {
            with(binding){
                tvDate.text = ticket.departureDate?.let { convertDateFormat(it) }
                tvDepartureTime.text = ticket.departureTime?.let { convertTimeFormat(it) }
                tvArrivalTime.text = ticket.arrivalTime?.let { convertTimeFormat(it) }

                tvDuration.text = ticket.flightDuration
            }
        }
    }

}