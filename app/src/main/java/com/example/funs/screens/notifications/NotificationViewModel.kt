package com.example.funs.screens.notifications

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.funs.utils.DataStoreManager
import com.example.funs.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(application: Application) : AndroidViewModel(application) {
    var message = mutableStateOf("")
    var isLoading = mutableStateOf(true)
    var showToast = mutableStateOf(false)
    var refreshing = mutableStateOf(false)
    var refreshUnReadNoti = mutableStateOf("yes")
    val unreadNotifications = MutableLiveData<Int>().apply { value = 0 }
    var notifications = MutableLiveData<MutableList<Notification>>().apply { value = mutableListOf() }
    private val dataStoreManager: DataStoreManager = DataStoreManager(application)
    val totalNotificationCount = dataStoreManager.totalNotificationCount().asLiveData(Dispatchers.IO)

    fun getNotifications(customerId: String, customerToken: String) {
        showToast.value = false
        message.value = ""
        refreshing.value = true

        viewModelScope.launch {
            val notificationService = NotificationService.getInstance()

            try {
                val response = notificationService.getNotifications(
                    customerId,
                    customerToken,
                    Utils.contentType
                )

                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        notifications.value = it
                    }
                    isLoading.value = false
                    refreshing.value = false

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), NotificationService::class.java)
                    }
                    showToast.value = true
                    isLoading.value = false
                    refreshing.value = false
                    message.value = error?.toString().toString()
                }
            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
                isLoading.value = false
                refreshing.value = false
            }
        }
    }

    fun markAsRead(customerToken: String, notificationId: String) {
        showToast.value = false
        message.value = ""
        refreshUnReadNoti.value = "no"

        viewModelScope.launch {
            val notificationService = NotificationService.getInstance()
            try {
                val response = notificationService.markAsRead(
                    notificationId,
                    customerToken,
                    Utils.contentType
                )

                if (response.isSuccessful) {
                    isLoading.value = false
                    refreshing.value = false
                    refreshUnReadNoti.value = "no"

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), NotificationService::class.java)
                    }
                    showToast.value = true
                    isLoading.value = false
                    refreshUnReadNoti.value = "no"
                    message.value = error?.toString().toString()
                }
            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
                isLoading.value = false
                refreshing.value = false
            }
        }
    }

    fun getUnreadNotifications(customerId: String, customerToken: String) {
        refreshUnReadNoti.value = "no"
        viewModelScope.launch {
            val notificationService = NotificationService.getInstance()

            try {
                val response = notificationService.getUnreadNotifications(
                    customerId,
                    customerToken,
                    Utils.contentType
                )

                if (response.isSuccessful) {
                    response.body()?.count?.let {
                        unreadNotifications.postValue(it)
                        setNotiCount(it)
                    }
                    refreshUnReadNoti.value = "no"

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), NotificationService::class.java)
                    }
                    refreshUnReadNoti.value = "no"
                }
                
            } catch (e: Exception) {
                message.value = e.message.toString()
            }
        }
    }

    private fun setNotiCount(it: Int) {
           viewModelScope.launch {
               dataStoreManager.setNotificationCount(it)
           }
    }
}
