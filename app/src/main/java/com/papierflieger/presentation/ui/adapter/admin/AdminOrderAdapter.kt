package com.papierflieger.presentation.ui.adapter.admin

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.orders.Order
import com.papierflieger.databinding.ItemAdminOrderBinding
import com.papierflieger.wrapper.convertDateFormat

class AdminOrderAdapter : RecyclerView.Adapter<AdminOrderAdapter.OrderViewHolder>() {

    private var diffCallback = object : DiffUtil.ItemCallback<Order>(){
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.passengerName == newItem.passengerName
        }

        override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private lateinit var onAdminOrderItem: OnAdminOrderItem

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value : List<Order?>?) = differ.submitList(value)

    fun actionClick(onAdminOrderItem: OnAdminOrderItem){
        this.onAdminOrderItem = onAdminOrderItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemAdminOrderBinding.inflate(LayoutInflater.from(
            parent.context), parent, false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bindingItem(item, position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class OrderViewHolder(
        private val binding : ItemAdminOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindingItem(item: Order?, position: Int) {
            with(binding){
                if (position < 1) vGap.visibility = View.INVISIBLE
                else vGap.visibility = View.GONE

                tvPassengerNameValue.text = item?.passengerName
                tvIdentityValue.text = item?.NIK
                tvDateBirthValue.text = item?.birthDate?.let { convertDateFormat(it) }
                tvNationalityValue.text = item?.nationality
                tvPassportNumberValue.text = item?.passportNumber
                tvExpirationDateValue.text = item?.expired?.let { convertDateFormat(it) }
                tvIssuingCountryValue.text = item?.issuingCountry
                if (item?.ticketId?.size != null && item.ticketId.size > 1) {
                    tvTicketValue.text = "Round Trip"
                } else {
                    tvTicketValue.text = "One Way"
                }


                if (item?.id != null) {
                    btnDelete.setOnClickListener { onAdminOrderItem.itemDeleteClicked(item.id) }
                }
            }
        }
    }

    interface OnAdminOrderItem {
        fun itemDeleteClicked(id : Int)
    }

}