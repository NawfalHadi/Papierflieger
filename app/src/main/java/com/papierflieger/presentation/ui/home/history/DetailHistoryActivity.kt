package com.papierflieger.presentation.ui.home.history

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.papierflieger.data.network.response.transaction.Ticket
import com.papierflieger.data.network.response.transaction.Transaction
import com.papierflieger.databinding.FragmentDetailHistoryBinding
import com.papierflieger.presentation.bussiness.OrderViewModel
import com.papierflieger.presentation.bussiness.TicketViewModel
import com.papierflieger.presentation.ui.adapter.histories.DetailTransactionsAdapter
import com.papierflieger.wrapper.Resource
import com.papierflieger.wrapper.convertDateFormat
import com.papierflieger.wrapper.convertToRupiah
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding : FragmentDetailHistoryBinding

    private val ticketViewModel : TicketViewModel by viewModels()
    private val orderViewModel : OrderViewModel by viewModels()

    private val listOfTicket : ArrayList<Int> = arrayListOf()
    private val detailTransactionsAdapter : DetailTransactionsAdapter by lazy { DetailTransactionsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mBundle = intent.extras

        val ticket = mBundle?.getParcelable<Ticket>(TICKET_LIST_KEY)

        bindingInformation(ticket)
        binding.icKeyboardArrow.setOnClickListener {
            pricingDetailsShow()
        }


    }

    private fun bindingInformation(ticket: Ticket?) {
        with(binding){
            tvFromlocation.text = ticket?.from?.city.toString()
            tvDestinationlocation.text = ticket?.from?.city.toString()

            tvOrdernumber.text = ticket?.ticketNumber.toString()
            tvPricingdetail.text = convertToRupiah(ticket?.price)

//            tvPurchasetime.text = convertDateFormat(transaction?.updatedAt.toString())
        }

//        getOrder(transaction?.orderId?.get(0))
    }

    private fun getOrder(id: Int?) {
        orderViewModel.getOrderDetail(id!!).observe(this){
            when(it){
                is Resource.Success -> {
                    it.payload?.order?.ticketId?.let { ticket -> listOfTicket.addAll(ticket) }
                    Log.e("AKH",  listOfTicket.toString())
                }
                else -> {}
            }
        }

        getTicket(listOfTicket)
    }

    private fun getTicket(listOfTicket: java.util.ArrayList<Int>) {
        for (i in listOfTicket){
            ticketViewModel.getTicketById(i).observe(this){
                when(it){
                    is Resource.Success -> {
                        detailTransactionsAdapter.setItem(it.payload?.ticket!!)
                        initRecyclers()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initRecyclers() {
        with(binding.rvListDetailtransaction){
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            adapter = detailTransactionsAdapter
        }
    }

    private fun pricingDetailsShow() {
        val arrowState = binding.icKeyboardArrow.rotation
        with(binding){
            if (arrowState == 180f){
                // Hide Details
                icKeyboardArrow.rotation = 0f
                // guideline, rv, payment hide
                guidelineOne.visibility = View.GONE
                rvPricingdetail.visibility = View.GONE
            } else {
                // Show Detail
                icKeyboardArrow.rotation = 180f
                // guideline, rv, payment show
                guidelineOne.visibility = View.VISIBLE
                rvPricingdetail.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val ORDER_LIST_KEY = "ORDER_LIST_KEY"
        const val TRANSACTION_LIST_KEY = "TRANSACTION_LIST_KEY"
        const val TICKET_LIST_KEY = "TICKET_LIST_KEY"
    }
}