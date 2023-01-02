package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.papierflieger.data.local.model.OrderDomestic
import com.papierflieger.data.local.model.OrderInternational
import com.papierflieger.data.network.response.orders.OrderResponse
import com.papierflieger.data.network.response.transaction.TransactionsResponse
import com.papierflieger.data.repository.OrderRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    fun continuePaymentInternational(
        token : String, orderInternational: OrderInternational
    ) : LiveData<Resource<OrderResponse>> {
        return orderRepository.continuePaymentInternational(
            token, orderInternational
        )
    }

    fun continuePaymentDomestic(
        token: String, orderDomestic: OrderDomestic
    ) : LiveData<Resource<OrderResponse>> {
        return orderRepository.continuePaymentDomestic(
            token, orderDomestic
        )
    }

    fun confirmPaymentMethod(
        token: String, bankName: String, accountName: String ,accountNumber: Int, tokenTransaction: String
    ) : LiveData<Resource<TransactionsResponse>> {
        return orderRepository.confirmPaymentMethod(
            token, bankName, accountName, accountNumber, tokenTransaction
        )
    }


}