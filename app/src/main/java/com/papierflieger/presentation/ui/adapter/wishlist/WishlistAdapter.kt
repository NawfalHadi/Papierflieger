package com.papierflieger.presentation.ui.adapter.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.papierflieger.R
import com.papierflieger.data.network.response.wishlist.Wishlist
import com.papierflieger.databinding.ItemWishlistBinding
import com.papierflieger.presentation.ui.adapter.destinations.AdminAirplaneAdapter

class WishlistAdapter : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {

    private var diffCallback = object : DiffUtil.ItemCallback<Wishlist>(){
        override fun areItemsTheSame(oldItem: Wishlist, newItem: Wishlist): Boolean {
            return oldItem.destination == newItem.destination
        }

        override fun areContentsTheSame(oldItem: Wishlist, newItem: Wishlist): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private lateinit var onWishlistItem: OnWishlistItem

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value : List<Wishlist?>) = differ.submitList(value)

    fun actionClick(onWishlistItem: OnWishlistItem){
        this.onWishlistItem = onWishlistItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishlistViewHolder {
        val binding = ItemWishlistBinding.inflate(LayoutInflater.from(
            parent.context), parent, false
        )
        return WishlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bindingItem(item, position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class WishlistViewHolder(
        private val binding : ItemWishlistBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindingItem(item: Wishlist?, position: Int) {
            with(binding){
                if (position < 1) vGap.visibility = View.INVISIBLE
                else vGap.visibility = View.GONE

                if (!item?.destination?.image?.get(0).isNullOrEmpty()) {
                    ivImage.load(item?.destination?.image?.get(0)) {
                        placeholder(R.color.background_gray)
                    }
                }

                tvDestinationName.text = item?.destination?.name
                tvLocation.text = item?.destination?.location
                tvDescriptionValue.text = item?.destination?.description

                if (item?.destinationId != null) {
                    cbFavorite.setOnClickListener { onWishlistItem.wishlistClicked(item.destinationId) }
                    binding.root.setOnClickListener { onWishlistItem.itemWishlistClicked(item.destinationId) }
                }
            }
        }
    }

    interface OnWishlistItem {
        fun itemWishlistClicked(id : Int)
        fun wishlistClicked(id : Int)
    }

}