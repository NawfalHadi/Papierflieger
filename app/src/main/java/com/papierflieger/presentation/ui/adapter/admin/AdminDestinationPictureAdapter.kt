package com.papierflieger.presentation.ui.adapter.admin

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.papierflieger.R
import com.papierflieger.databinding.ItemAddDestinationPictureBinding

class AdminDestinationPictureAdapter : RecyclerView.Adapter<AdminDestinationPictureAdapter.DestinationPictureViewHolder>() {

    private var list : ArrayList<String?> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(data : List<String?>){
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DestinationPictureViewHolder {
        val binding = ItemAddDestinationPictureBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DestinationPictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinationPictureViewHolder, position: Int) {
        val item = list[position]
        holder.bindingView(item, position)
    }

    override fun getItemCount(): Int = list.size

    class DestinationPictureViewHolder(
        private val binding : ItemAddDestinationPictureBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bindingView(item: Any?, position: Int) {
            with(binding){
                if (position < 1) vGap.visibility = View.INVISIBLE
                else vGap.visibility = View.GONE

                ivImage.load(item) {
                    placeholder(R.color.background_gray)
                }
            }
        }
    }

}