package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.local.model.OrderDomestic
import com.papierflieger.data.local.model.OrderInternational
import com.papierflieger.data.network.response.orders.OrderDetailResponse
import com.papierflieger.data.network.response.orders.OrderResponse
import com.papierflieger.data.network.response.transaction.PaymentMethod
import com.papierflieger.data.network.response.transaction.TransactionsResponse
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRepository(
    private val apiService: ApiService
) {
    private var orderResponse : MutableLiveData<Resource<OrderResponse>> = MutableLiveData()
    private var orderDetailResponse : MutableLiveData<Resource<OrderDetailResponse>> = MutableLiveData()
    private var paymentMethodResponse : MutableLiveData<Resource<PaymentMethod>> = MutableLiveData()

    fun continuePaymentDomestic(
        token: String, orderDomestic: OrderDomestic
    ) : LiveData<Resource<OrderResponse>> {
        apiService.continuePaymentDomestic(token, orderDomestic).enqueue(
            object : Callback<OrderResponse> {
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {
                    if (response.isSuccessful){
                        orderResponse.postValue(Resource.Success(response.body()!!))
                    }
                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    orderResponse.postValue(Resource.Error(t))
                }
            }
        )
        return orderResponse
    }

    fun continuePaymentInternational(
        token: String, orderInternational: OrderInternational
    ) : LiveData<Resource<OrderResponse>> {
        apiService.continuePaymentInternational(token, orderInternational).enqueue(
            object : Callback<OrderResponse> {
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {
                    if (response.isSuccessful){
                        orderResponse.postValue(Resource.Success(response.body()!!))
                    }
                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    orderResponse.postValue(Resource.Error(t))
                }
            }
        )
        return orderResponse
    }

    fun confirmPaymentMethod(
        token: String, bankName: String, accountName: String ,accountNumber: Int, tokenTransaction: String
    ) : MutableLiveData<Resource<PaymentMethod>> {
        apiService.confirmPaymentMethod(token, bankName, accountName, accountNumber, tokenTransaction).enqueue(
            object : Callback<PaymentMethod>{
                override fun onResponse(
                    call: Call<PaymentMethod>,
                    response: Response<PaymentMethod>
                ) {
                    if (response.isSuccessful){
                        paymentMethodResponse.postValue(Resource.Success(response.body()!!))
                    }
                }

                override fun onFailure(call: Call<PaymentMethod>, t: Throwable) {
                    paymentMethodResponse.postValue(Resource.Error(t))
                }

            }
        )

        return paymentMethodResponse
    }

    fun getDetailOrder(
        id : Int
    ): LiveData<Resource<OrderDetailResponse>> {
        apiService.getOrderById(id).enqueue(
            object : Callback<OrderDetailResponse>{
                override fun onResponse(
                    call: Call<OrderDetailResponse>,
                    response: Response<OrderDetailResponse>
                ) {
                    if (response.isSuccessful){
                        try {
                            orderDetailResponse.postValue(Resource.Success(response.body()!!))
                        } catch (t : Throwable){
                            orderDetailResponse.postValue(Resource.Error(t))
                        }
                    }
                }

                override fun onFailure(call: Call<OrderDetailResponse>, t: Throwable) {
                    orderDetailResponse.postValue(Resource.Error(t))
                }

            }
        )
        return orderDetailResponse
    }
}