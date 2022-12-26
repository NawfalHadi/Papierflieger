package com.papierflieger.wrapper

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.papierflieger.data.network.response.airplane.DataAirplane
import com.papierflieger.data.network.response.airport.Airport
import java.text.DecimalFormat
import com.papierflieger.data.network.response.ticket.DataTicket
import com.papierflieger.data.network.response.ticket.TiketBerangkat
import com.papierflieger.data.network.response.ticket.TiketPulang
import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate(): String {
    val format = SimpleDateFormat(
        "EEE, dd MMM yyyy",
        Locale.getDefault()
    )
    return format.format(Date(this))
}

class CustomAdapter(context: Context, objects: List<Airport?>?)
    : ArrayAdapter<Airport?>(context, R.layout.simple_list_item_1, objects as MutableList<Airport?>) {
    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.simple_list_item_1, parent, false)

        val item = getItem(position)
        view.findViewById<TextView>(R.id.text1).text = convertAirport(item?.airportName.toString(), item?.cityCode.toString())

        return view
    }
}

class CustomAdapterAirplane(context: Context, objects: List<DataAirplane?>?)
    : ArrayAdapter<DataAirplane?>(context, R.layout.simple_list_item_1, objects as MutableList<DataAirplane?>) {
    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.simple_list_item_1, parent, false)

        val item = getItem(position)
        view.findViewById<TextView>(R.id.text1).text = convertAirport(item?.airplaneName.toString(), item?.airplaneCode.toString())

        return view
    }
}

fun convertToRupiah(amount: Int): String {
    val rupiahFormat = DecimalFormat.getCurrencyInstance(Locale("in", "ID")) as DecimalFormat
    rupiahFormat.applyPattern("¤ #,###")
    return rupiahFormat.format(amount)
}

fun convertAirport(airport: String, cityCode: String) : String {
    return "$airport ($cityCode)"
}

fun convertDateFormatUpload(date: String): String {
    val inputDate = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault()).parse(date)
    return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(inputDate!!)
}

fun convertDateFormat(date: String): String {
    val inputDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(date)
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(inputDate!!)
}

fun convertTimeFormat(time: String): String {
    val inputTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).parse(time)
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(inputTime!!)
}

fun convertTimeToString(hour: Int, minute: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(calendar.time)
}

fun convertTimeToHour(hour: Int, minute: Int): String {
    return if (hour == 0) {
        "$minute menit"
    } else {
        if (minute == 0) {
            "$hour jam"
        } else {
            "$hour jam $minute menit"
        }
    }
}

fun Long.toRequestDate(): String {
    val format = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    )
    return format.format(Date(this))
}

fun TiketBerangkat.toDataTicket(): DataTicket {
    return DataTicket(
        airplaneId = this.airplaneId,
        arrivalDate = this.arrivalDate,
        arrivalTime = this.arrivalTime,
        arrivalTimeAtTransit = this.arrivalTimeAtTransit,
        createdAt = this.createdAt,
        departureDate = this.departureDate,
        departureTime = this.departureTime,
        departureTimeFromTransit = this.departureTimeFromTransit,
        flightDuration = this.flightDuration,
        flightFrom = this.flightFrom,
        flightTo = this.flightTo,
        id = this.id,
        price = this.price,
        ticketNumber = this.ticketNumber,
        ticketType = this.ticketType,
        totalTransit = this.totalTransit,
        transitDuration = this.transitDuration,
        transitPoint = this.transitPoint,
        updatedAt = this.updatedAt,
        from = null,
        airplane = null,
        to = null,
        transit = null
    )
}

fun TiketPulang.toDataTicket(): DataTicket {
    return DataTicket(
        airplaneId = this.airplaneId,
        arrivalDate = this.arrivalDate,
        arrivalTime = this.arrivalTime,
        arrivalTimeAtTransit = this.arrivalTimeAtTransit,
        createdAt = this.createdAt,
        departureDate = this.departureDate,
        departureTime = this.departureTime,
        departureTimeFromTransit = this.departureTimeFromTransit,
        flightDuration = this.flightDuration,
        flightFrom = this.flightFrom,
        flightTo = this.flightTo,
        id = this.id,
        price = this.price,
        ticketNumber = this.ticketNumber,
        ticketType = this.ticketType,
        totalTransit = this.totalTransit,
        transitDuration = this.transitDuration,
        transitPoint = this.transitPoint,
        updatedAt = this.updatedAt,
        from = null,
        airplane = null,
        to = null,
        transit = null
    )
}
