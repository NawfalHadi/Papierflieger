package com.papierflieger.presentation.ui.adapter.destinations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.airplane.DataAirplane
import com.papierflieger.databinding.ItemAdminAirplaneBinding

class AdminAirplaneAdapter : RecyclerView.Adapter<AdminAirplaneAdapter.AirplaneViewHolder>() {

    private var diffCallback = object : DiffUtil.ItemCallback<DataAirplane>(){
        override fun areItemsTheSame(oldItem: DataAirplane, newItem: DataAirplane): Boolean {
            return oldItem.airplaneName == newItem.airplaneName
        }

        override fun areContentsTheSame(oldItem: DataAirplane, newItem: DataAirplane): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private lateinit var onAdminAirplaneItem: OnAdminAirplaneItem

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value : List<DataAirplane?>?) = differ.submitList(value)

    fun actionClick(onAdminAirplaneItem: OnAdminAirplaneItem){
        this.onAdminAirplaneItem = onAdminAirplaneItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirplaneViewHolder {
        val binding = ItemAdminAirplaneBinding.inflate(LayoutInflater.from(
            parent.context), parent, false
        )
        return AirplaneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AirplaneViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bindingItem(item, position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class AirplaneViewHolder(
        private val binding : ItemAdminAirplaneBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindingItem(item: DataAirplane?, position: Int) {
            with(binding){
                if (position < 1) vGap.visibility = View.INVISIBLE
                else vGap.visibility = View.GONE

                tvAirplaneNameValue.text = item?.airplaneName
                tvAirplaneCodeValue.text = item?.airplaneCode
                tvClassValue.text = item?.category

                if (item?.id != null) {
                    btnEdit.setOnClickListener { onAdminAirplaneItem.itemEditClicked(item.id) }
                    btnDelete.setOnClickListener { onAdminAirplaneItem.itemDeleteClicked(item.id) }
                }
            }
        }
    }

    interface OnAdminAirplaneItem {
        fun itemEditClicked(id : Int)
        fun itemDeleteClicked(id : Int)
    }

}