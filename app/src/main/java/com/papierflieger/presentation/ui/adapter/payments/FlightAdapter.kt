package com.papierflieger.presentation.ui.adapter.payments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.ticket.TicketsDetail
import com.papierflieger.databinding.ItemPaymentFlightDetailBinding

class FlightAdapter : RecyclerView.Adapter<FlightAdapter.FlightViewHolder>() {
    private var list : ArrayList<TicketsDetail> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(tickets : ArrayList<TicketsDetail>) {
        list.clear()
        list.addAll(tickets)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlightAdapter.FlightViewHolder {
        val binding = ItemPaymentFlightDetailBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FlightViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlightAdapter.FlightViewHolder, position: Int) {
        val ticket = list[position]
        holder.bindingInformation(ticket)
    }

    override fun getItemCount(): Int = list.size

    inner class FlightViewHolder(
        private val binding: ItemPaymentFlightDetailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindingInformation(ticket: TicketsDetail) {
            with(binding){
                tvDeparture.text = ticket.from?.city
                tvArrival.text = ticket.to?.city
                tvDate.text = ticket.departureDate
            }
        }

    }

}