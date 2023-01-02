package com.papierflieger.presentation.ui.adapter.tickets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.R
import com.papierflieger.data.network.response.ticket.TiketBerangkat
import com.papierflieger.databinding.ItemListFlightBinding
import com.papierflieger.wrapper.convertTimeFormat
import com.papierflieger.wrapper.convertToRupiah

class DepartureAdapter : RecyclerView.Adapter<DepartureAdapter.DepartureViewHolder>(){

    private lateinit var onTicketActionCallback: OnTicketActionCallback
    private var departure : String = ""
    private var arrival : String = ""

    private var diffCallback = object : DiffUtil.ItemCallback<TiketBerangkat>(){
        override fun areItemsTheSame(oldItem: TiketBerangkat, newItem: TiketBerangkat): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TiketBerangkat, newItem: TiketBerangkat): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    fun itemAction(onTicketActionCallback: OnTicketActionCallback){
        this.onTicketActionCallback = onTicketActionCallback
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setAirport(departure: String, arrival: String) {
        this.departure = departure
        this.arrival = arrival
    }
    fun setItem(value : List<TiketBerangkat?>) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartureViewHolder {
        val binding = ItemListFlightBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DepartureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartureViewHolder, position: Int) {
        val ticket = differ.currentList[position]
        holder.bindingItem(ticket)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class DepartureViewHolder(
        private val binding: ItemListFlightBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bindingItem(ticket: TiketBerangkat?) {
            with(binding){
                tvDepartureTime.text = ticket?.departureTime?.let { convertTimeFormat(it) }
                tvArrivalTime.text = ticket?.arrivalTime?.let { convertTimeFormat(it) }
                tvDuration.text = ticket?.flightDuration
                tvPrice.text = ticket?.price?.let { convertToRupiah(it) }
                tvDeparture.text = departure
                tvArrival.text = arrival

                if (ticket?.totalTransit != null && ticket.totalTransit >= 1) {
                    tvFlightStop.setText(R.string.text_transit)
                }

                cardTicket.setOnClickListener {
                    onTicketActionCallback.ticketClicked(ticket)
                }
            }
        }
    }

    interface OnTicketActionCallback{
        fun ticketClicked(ticket: TiketBerangkat?)
    }
}