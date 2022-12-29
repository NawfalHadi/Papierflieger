package com.papierflieger.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.papierflieger.data.network.response.ChangeDataResponse
import com.papierflieger.data.network.response.notification.CreateNotificationResponse
import com.papierflieger.data.network.response.notification.NotificationsResponse
import com.papierflieger.data.network.service.ApiService
import com.papierflieger.wrapper.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationRepository  (
    private val apiService : ApiService
) {

    private var notificationResponse : MutableLiveData<Resource<NotificationsResponse>> = MutableLiveData()
    private var createNotificationResponse : MutableLiveData<Resource<CreateNotificationResponse>> = MutableLiveData()
    private var updateNotificationResponse : MutableLiveData<Resource<ChangeDataResponse>> = MutableLiveData()

    fun getNotifications(
        token: String,
    ) : LiveData<Resource<NotificationsResponse>> {
        apiService.getNotifications(token).enqueue(object : Callback<NotificationsResponse> {
            override fun onResponse(
                call: Call<NotificationsResponse>,
                response: Response<NotificationsResponse>
            ) {
                if (response.isSuccessful){
                    notificationResponse.postValue(Resource.Success(response.body()!!))
                }
            }
            override fun onFailure(call: Call<NotificationsResponse>, t: Throwable) {
                notificationResponse.postValue(Resource.Error(t))
            }
        })
        return notificationResponse
    }

    fun createNotification(
        token: String,
        name: String,
        message: String,
    ) : LiveData<Resource<CreateNotificationResponse>> {
        apiService.createNotification(token, name, message).enqueue(object : Callback<CreateNotificationResponse> {
            override fun onResponse(
                call: Call<CreateNotificationResponse>,
                response: Response<CreateNotificationResponse>
            ) {
                if (response.isSuccessful){
                    createNotificationResponse.postValue(Resource.Success(response.body()!!))
                }
            }
            override fun onFailure(call: Call<CreateNotificationResponse>, t: Throwable) {
                createNotificationResponse.postValue(Resource.Error(t))
            }
        })
        return createNotificationResponse
    }

    fun updateNotification(
        token: String,
    ) : LiveData<Resource<ChangeDataResponse>> {
        apiService.updateNotification(token).enqueue(object : Callback<ChangeDataResponse> {
            override fun onResponse(
                call: Call<ChangeDataResponse>,
                response: Response<ChangeDataResponse>
            ) {
                if (response.isSuccessful){
                    updateNotificationResponse.postValue(Resource.Success(response.body()!!))
                }
            }
            override fun onFailure(call: Call<ChangeDataResponse>, t: Throwable) {
                updateNotificationResponse.postValue(Resource.Error(t))
            }
        })
        return updateNotificationResponse
    }

}