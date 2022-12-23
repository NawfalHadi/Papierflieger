package com.papierflieger.presentation.ui.adapter.destinations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.papierflieger.R
import com.papierflieger.data.network.response.destination.Destination
import com.papierflieger.databinding.ItemAdminDestinationBinding

class AdminDestinationAdapter : RecyclerView.Adapter<AdminDestinationAdapter.DestinationViewHolder>() {

    private var diffCallback = object : DiffUtil.ItemCallback<Destination>(){
        override fun areItemsTheSame(oldItem: Destination, newItem: Destination): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Destination, newItem: Destination): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private lateinit var onAdminDestinationItem: OnAdminDestinationItem
    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value : List<Destination?>) = differ.submitList(value)

    fun actionClick(onAdminDestinationItem: OnAdminDestinationItem){
        this.onAdminDestinationItem = onAdminDestinationItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val binding = ItemAdminDestinationBinding.inflate(LayoutInflater.from(
            parent.context), parent, false
        )
        return DestinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bindingItem(item, position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class DestinationViewHolder(
        private val binding : ItemAdminDestinationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindingItem(item: Destination?, position: Int) {
            with(binding){
                if (position >= 1) vGap.visibility = View.GONE

                if (item?.image?.size!! >= 2) {
                    ivImage1.load(item.image[0]) {
                        placeholder(R.color.gray)
                    }
                    ivImage2.load(item.image[1]) {
                        placeholder(R.color.gray)
                    }
                } else if (item.image.size == 1) {
                    ivImage1.load(item.image[0]) {
                        placeholder(R.color.gray)
                    }
                }

                tvDestinationNameValue.text = item.name
                tvAirportValue.text = item.airport?.airportName
                tvLocationValue.text = item.location
                tvDescriptionValue.text = item.description

                if (item.id != null) {
                    btnEdit.setOnClickListener { onAdminDestinationItem.itemEditClicked(item.id) }
                    btnDelete.setOnClickListener { onAdminDestinationItem.itemDeleteClicked(item.id) }
                }
            }
        }
    }

    interface OnAdminDestinationItem {
        fun itemEditClicked(id : Int)
        fun itemDeleteClicked(id : Int)
    }

}