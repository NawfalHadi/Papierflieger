package com.papierflieger.presentation.ui.adapter.histories

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.transaction.Ticket
import com.papierflieger.databinding.ItemPricingDetailTransactionBinding

class DetailTransactionsAdapter : RecyclerView.Adapter<DetailTransactionsAdapter.ViewHolder>(){
    private lateinit var onBoardingPassShows: OnBoardingPassShows
    private val list : ArrayList<Ticket> = arrayListOf()

    fun listener (onBoardingPassShows: OnBoardingPassShows){
        this.onBoardingPassShows = onBoardingPassShows
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(ticket: Ticket){
        list.add(ticket)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailTransactionsAdapter.ViewHolder {
        val binding = ItemPricingDetailTransactionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailTransactionsAdapter.ViewHolder, position: Int) {
        val ticket = list[position]
        holder.bindingInformation(ticket)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(
        private val binding : ItemPricingDetailTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bindingInformation(ticket: Ticket) {
            with(binding){
                tvFromlocation.text = ticket.from?.city.toString()
                tvDestinationlocation.text = ticket.to?.city.toString()

                cardTicket.setOnClickListener {
                    onBoardingPassShows.checkBoardingPass(ticket)
                }
            }
        }

    }

    interface OnBoardingPassShows {
        fun checkBoardingPass(ticket: Ticket)
    }

}