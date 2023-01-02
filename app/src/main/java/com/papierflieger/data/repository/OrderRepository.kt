package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.local.model.OrderDomestic
import com.papierflieger.data.local.model.OrderInternational
import com.papierflieger.data.network.response.orders.OrderDetailResponse
import com.papierflieger.data.network.response.orders.OrderResponse
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
    private var transactionsResponse : MutableLiveData<Resource<TransactionsResponse>> = MutableLiveData()

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
    ) : LiveData<Resource<TransactionsResponse>> {
        apiService.confirmPaymentMethod(token, bankName, accountName, accountNumber, tokenTransaction).enqueue(
            object : Callback<TransactionsResponse>{
                override fun onResponse(
                    call: Call<TransactionsResponse>,
                    response: Response<TransactionsResponse>
                ) {
                    if (response.isSuccessful){
                        transactionsResponse.postValue(Resource.Success(response.body()!!))
                    }
                }

                override fun onFailure(call: Call<TransactionsResponse>, t: Throwable) {
                    orderResponse.postValue(Resource.Error(t))
                }

            }
        )

        return transactionsResponse
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
                    orderDetailResponse.postValue(Resource.Success(response.body()!!))
                }

                override fun onFailure(call: Call<OrderDetailResponse>, t: Throwable) {
                    orderDetailResponse.postValue(Resource.Error(t))
                }

            }
        )
        return orderDetailResponse
    }
}