package com.papierflieger.presentation.ui.adapter.destinations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.airport.Airport
import com.papierflieger.databinding.ItemAdminAirportBinding

class AdminAirportAdapter : RecyclerView.Adapter<AdminAirportAdapter.AirportViewHolder>() {

    private var diffCallback = object : DiffUtil.ItemCallback<Airport>(){
        override fun areItemsTheSame(oldItem: Airport, newItem: Airport): Boolean {
            return oldItem.airportName == newItem.airportName
        }

        override fun areContentsTheSame(oldItem: Airport, newItem: Airport): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private lateinit var onAdminAirportItem: OnAdminAirportItem

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value : List<Airport?>?) = differ.submitList(value)

    fun actionClick(onAdminAirportItem: OnAdminAirportItem){
        this.onAdminAirportItem = onAdminAirportItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportViewHolder {
        val binding = ItemAdminAirportBinding.inflate(LayoutInflater.from(
            parent.context), parent, false
        )
        return AirportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AirportViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bindingItem(item, position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class AirportViewHolder(
        private val binding : ItemAdminAirportBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindingItem(item: Airport?, position: Int) {
            with(binding){
                if (position >= 1) vGap.visibility = View.GONE

                tvAirportNameValue.text = item?.airportName
                tvCityValue.text = item?.city
                tvCityCode.text = item?.cityCode

                if (item?.id != null) {
                    btnEdit.setOnClickListener { onAdminAirportItem.itemEditClicked(item.id) }
                    btnDelete.setOnClickListener { onAdminAirportItem.itemDeleteClicked(item.id) }
                }
            }
        }
    }

    interface OnAdminAirportItem {
        fun itemEditClicked(id : Int)
        fun itemDeleteClicked(id : Int)
    }

}