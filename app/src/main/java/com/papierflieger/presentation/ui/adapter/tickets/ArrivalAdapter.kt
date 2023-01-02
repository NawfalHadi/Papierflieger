package com.papierflieger.presentation.ui.adapter.tickets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.R
import com.papierflieger.data.network.response.ticket.TiketPulang
import com.papierflieger.databinding.ItemListFlightBinding
import com.papierflieger.wrapper.convertTimeFormat
import com.papierflieger.wrapper.convertToRupiah

class ArrivalAdapter : RecyclerView.Adapter<ArrivalAdapter.ArrivalViewHolder>() {

    private lateinit var onArrivalTicketActionCallback: OnArrivalTicketActionCallback
    private var departure : String = ""
    private var arrival : String = ""

    private var diffCallback = object : DiffUtil.ItemCallback<TiketPulang>(){
        override fun areItemsTheSame(oldItem: TiketPulang, newItem: TiketPulang): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TiketPulang, newItem: TiketPulang): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    fun itemAction(onArrivalTicketActionCallback: OnArrivalTicketActionCallback){
        this.onArrivalTicketActionCallback = onArrivalTicketActionCallback
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setAirport(departure: String, arrival: String) {
        this.departure = departure
        this.arrival = arrival
    }
    fun setItem(value : List<TiketPulang?>) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArrivalViewHolder {
        val binding = ItemListFlightBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ArrivalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArrivalViewHolder, position: Int) {
        val ticket = differ.currentList[position]
        holder.bindingItem(ticket)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ArrivalViewHolder(
        private var binding : ItemListFlightBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindingItem(ticket: TiketPulang?) {
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
                    onArrivalTicketActionCallback.ticketClicked(ticket!!)
                }
            }
        }
    }

    interface OnArrivalTicketActionCallback{
        fun ticketClicked(ticket: TiketPulang?)
    }
}