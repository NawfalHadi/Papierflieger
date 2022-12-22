package com.papierflieger.presentation.ui.adapter.tickets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.ticket.TiketBerangkat
import com.papierflieger.databinding.ItemListFlightBinding

class DepartureAdapter : RecyclerView.Adapter<DepartureAdapter.DepartureViewHolder>(){

    private lateinit var onTicketActionCallback: OnTicketActionCallback

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
                tvDepartureTime.text = ticket?.departureTime
                tvArrivalTime.text = ticket?.arrivalTime
                tvDuration.text = ticket?.flightDuration

                cardTicket.setOnClickListener {
                    onTicketActionCallback.ticketClicked(ticket!!)
                }
            }
        }
    }

    interface OnTicketActionCallback{
        fun ticketClicked(ticket: TiketBerangkat?)
    }
}