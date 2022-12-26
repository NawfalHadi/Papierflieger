package com.papierflieger.presentation.ui.adapter.airports

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.local.room.entity.AirportEntity
import com.papierflieger.databinding.ItemAirportSearchBinding

class AirportsAdapter: RecyclerView.Adapter<AirportsAdapter.AirportsViewHolder>() {

    private lateinit var onAirportItemAction: OnAirportItemAction
    private var list : ArrayList<AirportEntity> = arrayListOf()

    fun airportChoosed(onAirportItemAction: OnAirportItemAction){
        this.onAirportItemAction = onAirportItemAction
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(airports : ArrayList<AirportEntity>){
        list.clear()
        list.addAll(airports)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AirportsViewHolder {
        val binding = ItemAirportSearchBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AirportsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AirportsViewHolder, position: Int) {
        val airport = list[position]
        holder.bindingData(airport, onAirportItemAction)
    }

    override fun getItemCount(): Int = list.size

    class AirportsViewHolder(
        private val binding : ItemAirportSearchBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bindingData(airport: AirportEntity, onAirportItemAction: OnAirportItemAction) {
            with(binding){
                tvAirportName.text = airport.name
                tvCity.text = airport.city
                tvCityCode.text = airport.cityCode

                layoutItem.setOnClickListener{
                    onAirportItemAction.airportChoosed(airport)
                }
            }
        }

    }

    interface OnAirportItemAction {
        fun airportChoosed(airport: AirportEntity)
    }
}