package com.papierflieger.presentation.ui.adapter.destinations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.papierflieger.R
import com.papierflieger.data.network.response.Destination
import com.papierflieger.databinding.ItemFavoriteDestinationBinding

class DestinationAdapter : RecyclerView.Adapter<DestinationAdapter.DestionationViewHolder>() {

    private var diffCallback = object : DiffUtil.ItemCallback<Destination>(){
        override fun areItemsTheSame(oldItem: Destination, newItem: Destination): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Destination, newItem: Destination): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value : List<Destination?>) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestionationViewHolder {
        val binding = ItemFavoriteDestinationBinding.inflate(LayoutInflater.from(
            parent.context), parent, false
        )
        return DestionationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestionationViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bindingItem(item, position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    class DestionationViewHolder(
        private val binding : ItemFavoriteDestinationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindingItem(item: Destination?, position: Int) {
            with(binding){
                if (position >= 1) vGap1.visibility = View.GONE

                if (item?.image?.isNotEmpty() == true) {
                    itemIvDestinationImage.load(item.image[0]) {
                        placeholder(R.color.gray)
                    }
                }

                itemTvDestinationName.text = item?.name
                itemTvDestinationLocation.text = item?.location
                itemTvDestinationInformation.text = ""
            }
        }
    }

    interface OnDestinationFavItem {
        fun itemClicked(id : Int)
    }

}