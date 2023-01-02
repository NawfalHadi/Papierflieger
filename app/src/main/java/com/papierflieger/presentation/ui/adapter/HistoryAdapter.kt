package com.papierflieger.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.transaction.HistoriesResponse
import com.papierflieger.data.network.response.transaction.Order
import com.papierflieger.data.network.response.transaction.Ticket
import com.papierflieger.data.network.response.transaction.Transaction
import com.papierflieger.databinding.ItemTransactionHistoryBinding
import com.papierflieger.wrapper.convertToRupiah

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
        val transactions = transaction[position]
        val order = order[position]
        val ticket = ticket[position]

        holder.bindingInformation(transactions, order, ticket)
    }

    override fun getItemCount(): Int = transaction.size

    inner class ViewHolder(
        private val binding : ItemTransactionHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindingInformation(history: Transaction, order: Order, ticket: Ticket) {
            with(binding){
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