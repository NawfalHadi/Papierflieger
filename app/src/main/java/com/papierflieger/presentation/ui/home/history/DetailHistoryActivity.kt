package com.papierflieger.presentation.ui.home.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.papierflieger.data.network.response.transaction.Order
import com.papierflieger.data.network.response.transaction.Ticket
import com.papierflieger.data.network.response.transaction.Transaction
import com.papierflieger.databinding.FragmentDetailHistoryBinding
import com.papierflieger.presentation.bussiness.TicketViewModel
import com.papierflieger.wrapper.convertDateFormat
import com.papierflieger.wrapper.convertToRupiah
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding : FragmentDetailHistoryBinding

    private val ticketViewModel : TicketViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mBundle = intent.extras

        val ticket = mBundle?.getParcelable<Ticket>(TICKET_LIST_KEY)
        val order = mBundle?.getParcelable<Order>(ORDER_LIST_KEY)
        val transaction = mBundle?.getParcelable<Transaction>(TRANSACTION_LIST_KEY)

        bindingInformation(ticket, order, transaction)
        binding.icKeyboardArrow.setOnClickListener {
            pricingDetailsShow()
        }

    }

    private fun bindingInformation(ticket: Ticket?, order: Order?, transaction: Transaction?) {
        with(binding){
            tvFromlocation.text = ticket?.from?.city.toString()
            tvDestinationlocation.text = ticket?.from?.city.toString()

            tvOrdernumber.text = ticket?.ticketNumber.toString()
            tvPricingdetail.text = convertToRupiah(transaction?.totalPrice)

            tvPurchasetime.text = convertDateFormat(transaction?.updatedAt.toString())

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