package com.papierflieger.presentation.ui.adapter.passenger

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.databinding.ItemProfileAccountBinding

class TravelerAdapter : RecyclerView.Adapter<TravelerAdapter.TravelerViewHolder>() {

    private lateinit var onTravelCardListener: OnTravelCardListener
    private var list : ArrayList<String> = arrayListOf()

    fun listener(onTravelCardListener: OnTravelCardListener){
        this.onTravelCardListener = onTravelCardListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(cardInformation : ArrayList<String>){
        list.clear()
        list.addAll(cardInformation)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelerViewHolder {
        val binding = ItemProfileAccountBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TravelerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelerViewHolder, position: Int) {
        val information = list[position]
        holder.bindingItem(information, position)
    }

    override fun getItemCount(): Int = list.size

    inner class TravelerViewHolder(
        private val binding : ItemProfileAccountBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bindingItem(information: String, position: Int) {
            with(binding){
                tvAccount.text = information
                cardAction.setOnClickListener {
                    onTravelCardListener.gotoForm(position)
                }
            }
        }
    }

    interface OnTravelCardListener {
        fun gotoForm(position: Int)
    }
}