package com.papierflieger.presentation.ui.adapter.payments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.databinding.ItemPricingDetailsTextBinding
import com.papierflieger.wrapper.convertToRupiah

class PassengerInformationAdapter : RecyclerView.Adapter<PassengerInformationAdapter.ViewHolder>() {
    private var listPrice : ArrayList<Int> = arrayListOf()
    private var list : ArrayList<String> = arrayListOf()

    fun setItem(passenger : ArrayList<String>, price : ArrayList<Int>){
        list.clear()
        list.addAll(passenger)

        listPrice.clear()
        listPrice.addAll(price)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPricingDetailsTextBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val passenger = list[position]
        val prices = listPrice[position]
        holder.bindingInformation(passenger, prices)
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(
        private val binding : ItemPricingDetailsTextBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bindingInformation(passenger: String, prices: Int) {
            with(binding){
                tvPricingdetailInformation.text = passenger
                tvPricingdetailPrice.text = convertToRupiah(prices)
            }
        }

    }
}