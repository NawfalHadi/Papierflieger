package com.papierflieger.presentation.ui.adapter.tickets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.ticket.TiketPulang
import com.papierflieger.databinding.ItemListFlightBinding

class ArrivalAdapter : RecyclerView.Adapter<ArrivalAdapter.ArrivalViewHolder>() {

    private lateinit var onArrivalTicketActionCallback: OnArrivalTicketActionCallback

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

    class ArrivalViewHolder(
        private var binding : ItemListFlightBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindingItem(ticket: TiketPulang?) {
            with(binding){
                tvDepartureTime.text = ticket?.departureTime
                tvArrivalTime.text = ticket?.arrivalTime
                tvDuration.text = ticket?.flightDuration


                cardTicket.setOnClickListener {

                }
            }
        }
    }

    interface OnArrivalTicketActionCallback{
        fun ticketClicked(ticket: TiketPulang?)
    }
}