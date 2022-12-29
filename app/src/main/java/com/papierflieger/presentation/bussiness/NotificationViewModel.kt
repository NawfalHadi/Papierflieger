package com.papierflieger.presentation.bussiness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.papierflieger.data.network.response.ChangeDataResponse
import com.papierflieger.data.network.response.notification.CreateNotificationResponse
import com.papierflieger.data.network.response.notification.NotificationsResponse
import com.papierflieger.data.repository.NotificationRepository
import com.papierflieger.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository : NotificationRepository
) : ViewModel() {

    fun getNotifications(
        token: String
    ): LiveData<Resource<NotificationsResponse>> {
        return notificationRepository.getNotifications(
            token
        )
    }

    fun createNotification(
        token: String,
        name: String,
        message: String,
    ): LiveData<Resource<CreateNotificationResponse>> {
        return notificationRepository.createNotification(
            token, name, message
        )
    }

    fun updateNotification(
        token: String,
    ): LiveData<Resource<ChangeDataResponse>> {
        return notificationRepository.updateNotification(
            token
        )
    }

}