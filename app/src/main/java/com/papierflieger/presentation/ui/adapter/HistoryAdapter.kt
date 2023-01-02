package com.papierflieger.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.transaction.Transaction
import com.papierflieger.data.network.response.transaction.Transaksi
import com.papierflieger.databinding.ItemPaymentFlightDetailBinding
import com.papierflieger.databinding.ItemTransactionHistoryBinding
import com.papierflieger.wrapper.convertToRupiah

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    private val list : ArrayList<Transaction> = arrayListOf()

    fun setItem(histories : ArrayList<Transaction>){
        list.clear()
        list.addAll(histories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val binding = ItemTransactionHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        val history = list[position]
        holder.bindingInformation(history)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(
        private val binding : ItemTransactionHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindingInformation(history: Transaction) {
            with(binding){
                tvOrdernumber.text = history.orderId.toString()
                tvTranscationprice.text = convertToRupiah(history.totalPrice!!.toInt())
            }
        }

    }

}