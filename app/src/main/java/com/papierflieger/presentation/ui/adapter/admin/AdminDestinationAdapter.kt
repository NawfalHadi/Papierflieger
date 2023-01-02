package com.papierflieger.presentation.ui.adapter.admin

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
import com.papierflieger.wrapper.convertAirport

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
                if (position < 1) vGap.visibility = View.INVISIBLE
                else vGap.visibility = View.GONE

                tvDestinationNameValue.text = item?.name
                tvAirportValue.text = convertAirport(
                    item?.airport?.airportName.toString(),
                    item?.airport?.cityCode.toString())
                tvLocationValue.text = item?.location
                tvDescriptionValue.text = item?.description

                if (item?.id != null) {
                    btnEdit.setOnClickListener { onAdminDestinationItem.itemEditClicked(item.id) }
                    btnDelete.setOnClickListener { onAdminDestinationItem.itemDeleteClicked(item.id) }
                }

                if (item?.image.isNullOrEmpty()) {
                    ivImage1.visibility = View.INVISIBLE
                    ivImage2.visibility = View.INVISIBLE
                } else {
                    if (item?.image?.size!! >= 2) {
                        ivImage1.load(item.image[0]) {
                            placeholder(R.color.background_gray)
                        }
                        ivImage2.load(item.image[1]) {
                            placeholder(R.color.background_gray)
                        }
                    } else {
                        ivImage1.load(item.image[0]) {
                            placeholder(R.color.background_gray)
                        }
                        ivImage2.visibility = View.INVISIBLE
                    }

                }
            }
        }
    }

    interface OnAdminDestinationItem {
        fun itemEditClicked(id : Int)
        fun itemDeleteClicked(id : Int)
    }

}