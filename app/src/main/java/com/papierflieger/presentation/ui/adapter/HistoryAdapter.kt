package com.papierflieger.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.transaction.HistoriesResponse
import com.papierflieger.data.network.response.transaction.Order
import com.papierflieger.data.network.response.transaction.Ticket
import com.papierflieger.data.network.response.transaction.Transaction
import com.papierflieger.databinding.ItemTransactionHistoryBinding
import com.papierflieger.wrapper.convertAirport

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    private lateinit var onHistoryCardAction : OnHistoryCardAction

    private val transaction : ArrayList<Transaction> = arrayListOf()
    private val ticket : ArrayList<Ticket> = arrayListOf()
    private val order : ArrayList<Order> = arrayListOf()

    fun listener(onHistoryCardAction: OnHistoryCardAction){
        this.onHistoryCardAction = onHistoryCardAction
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(histories : HistoriesResponse){
        transaction.clear()
        ticket.clear()
        order.clear()

        transaction.addAll(histories.transaction)
        ticket.addAll(histories.ticket)
        order.addAll(histories.order)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val binding = ItemTransactionHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        val ticket = ticket[position]
        holder.bindingInformation(ticket)
    }

    override fun getItemCount(): Int = transaction.size

    inner class ViewHolder(
        private val binding : ItemTransactionHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindingInformation(ticket: Ticket) {
            with(binding){
                tvOrdernumber.text = "#${ticket.id.toString()}"
                tvFromLocation.text = convertAirport(
                    ticket.from?.city?.substringBefore(",").toString(),
                    ticket.from?.cityCode.toString())
                tvDestinationLocation.text = convertAirport(
                    ticket.to?.city?.substringBefore(",").toString(),
                    ticket.to?.cityCode.toString())
                if (ticket.totalTransit == null) {
                    icChangeArrow.visibility = View.INVISIBLE
                    icDirect.visibility = View.VISIBLE
                }
                tvDestinationLocation.text = ticket.to?.city
                tvFromLocation.text = ticket.from?.city
                tvOrdernumber.text = ticket.ticketNumber.toString()
            }
        }
    }

    interface OnHistoryCardAction {
        fun cardAction()
    }

}