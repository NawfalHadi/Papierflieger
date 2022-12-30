package com.papierflieger.presentation.ui.adapter.admin

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.papierflieger.data.network.response.ticket.DataTicket
import com.papierflieger.databinding.ItemAdminTicketBinding
import com.papierflieger.wrapper.convertAirport
import com.papierflieger.wrapper.convertDateFormat
import com.papierflieger.wrapper.convertTimeFormat
import com.papierflieger.wrapper.convertToRupiah

class AdminTicketAdapter : RecyclerView.Adapter<AdminTicketAdapter.TicketViewHolder>() {

    private var diffCallback = object : DiffUtil.ItemCallback<DataTicket>(){
        override fun areItemsTheSame(oldItem: DataTicket, newItem: DataTicket): Boolean {
            return oldItem.ticketNumber == newItem.ticketNumber
        }

        override fun areContentsTheSame(oldItem: DataTicket, newItem: DataTicket): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private lateinit var onAdminTicketItem: OnAdminTicketItem

    private val differ = AsyncListDiffer(this, diffCallback)
    fun setItem(value : List<DataTicket?>?) = differ.submitList(value)

    fun actionClick(onAdminTicketItem: OnAdminTicketItem){
        this.onAdminTicketItem = onAdminTicketItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = ItemAdminTicketBinding.inflate(LayoutInflater.from(
            parent.context), parent, false
        )
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bindingItem(item, position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class TicketViewHolder(
        private val binding : ItemAdminTicketBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindingItem(item: DataTicket?, position: Int) {
            with(binding) {

                if (position < 1) vGap.visibility = View.INVISIBLE
                else vGap.visibility = View.GONE

                if (item?.transitPoint.toString() != "null") {
                    cvTicket.visibility = View.VISIBLE
                    cvTicketDirect.visibility = View.GONE

                    tvFlightTypeValue.text = "Transit"
                    tvTicketTypeValue.text = item?.ticketType

                    tvTicketNumberValue.text = item?.ticketNumber.toString()
                    tvAirplaneNameValue.text = item?.airplane?.airplaneName

                    tvDepartureTimeValue.text = convertTimeFormat(item?.departureTime.toString())
                    tvDepartureDateValue.text = convertDateFormat(item?.departureDate.toString())
                    tvFlightFromValue.text = convertAirport(item?.from?.airportName.toString(), item?.from?.cityCode.toString())

                    tvTransitArrivalTimeValue.text = convertTimeFormat(item?.arrivalTimeAtTransit.toString())
                    tvTransitDepartureTimeValue.text = convertTimeFormat(item?.departureTimeFromTransit.toString())
                    tvTransitPointValue.text = convertAirport(item?.transit?.airportName.toString(), item?.transit?.cityCode.toString())

                    tvArrivalTimeValue.text = convertTimeFormat(item?.arrivalTime.toString())
                    tvArrivalDateValue.text = convertDateFormat(item?.arrivalDate.toString())
                    tvFlightToValue.text = convertAirport(item?.to?.airportName.toString(), item?.to?.cityCode.toString())

                    tvClassValue.text = item?.airplane?.category
                    tvPriceValue.text = convertToRupiah(item?.price!!)

                    if (item.id != null) {
                        btnEdit.setOnClickListener { onAdminTicketItem.itemEditClicked(item.id) }
                        btnDelete.setOnClickListener { onAdminTicketItem.itemDeleteClicked(item.id) }
                    }
                } else {
                    cvTicket.visibility = View.GONE
                    cvTicketDirect.visibility = View.VISIBLE

                    tvFlightTypeValueDirect.text = "Direct"
                    tvTicketTypeValueDirect.text = item?.ticketType

                    tvTicketNumberValueDirect.text = item?.ticketNumber.toString()
                    tvAirplaneNameValueDirect.text = item?.airplane?.airplaneName

                    tvDepartureTimeValueDirect.text = convertTimeFormat(item?.departureTime.toString())
                    tvDepartureDateValueDirect.text = convertDateFormat(item?.departureDate.toString())
                    tvFlightFromValueDirect.text = convertAirport(item?.from?.airportName.toString(), item?.from?.cityCode.toString())

                    tvArrivalTimeValueDirect.text = convertTimeFormat(item?.arrivalTime.toString())
                    tvArrivalDateValueDirect.text = convertDateFormat(item?.arrivalDate.toString())
                    tvFlightToValueDirect.text = convertAirport(item?.to?.airportName.toString(), item?.to?.cityCode.toString())

                    tvClassValueDirect.text = item?.airplane?.category
                    tvPriceValueDirect.text = convertToRupiah(item?.price!!)

                    if (item.id != null) {
                        btnEditDirect.setOnClickListener { onAdminTicketItem.itemEditClicked(item.id) }
                        btnDeleteDirect.setOnClickListener { onAdminTicketItem.itemDeleteClicked(item.id) }
                    }
                }
            }
        }
    }

    interface OnAdminTicketItem {
        fun itemEditClicked(id : Int)
        fun itemDeleteClicked(id : Int)
    }

}