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
import com.papierflieger.databinding.ItemFavoriteDestinationBinding

class DestinationAdapter : RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder>() {

    private var diffCallback = object : DiffUtil.ItemCallback<Destination>(){
        override fun areItemsTheSame(oldItem: Destination, newItem: Destination): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Destination, newItem: Destination): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private lateinit var onDestinationFavItem: OnDestinationFavItem

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value : List<Destination?>) = differ.submitList(value)

    fun actionClick(onDestinationFavItem: OnDestinationFavItem){
        this.onDestinationFavItem = onDestinationFavItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val binding = ItemFavoriteDestinationBinding.inflate(LayoutInflater.from(
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
        private val binding : ItemFavoriteDestinationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindingItem(item: Destination?, position: Int) {
            with(binding){
                if (position < 1) vGap1.visibility = View.INVISIBLE
                else vGap1.visibility = View.GONE

                if (item?.image?.isNotEmpty() == true) {
                    itemIvDestinationImage.load(item.image[0]) {
                        placeholder(R.color.background_gray)
                    }
                }

                tvDestinationName.text = item?.name
                tvLocation.text = item?.location
                tvDescription.text = item?.description

                binding.root.setOnClickListener {
                    item?.id?.let { id -> onDestinationFavItem.itemClicked(id) }
                }
            }
        }
    }

    interface OnDestinationFavItem {
        fun itemClicked(id : Int)
    }

}