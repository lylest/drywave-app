package com.example.funs.app

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.funs.utils.DataStoreManager
import com.example.funs.utils.Utils
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AppViewModel(
    application: Application
): AndroidViewModel(application) {

    private val dataStoreManager: DataStoreManager = DataStoreManager(application)
    val currentUserToken = dataStoreManager.getUserToken().asLiveData(Dispatchers.IO)
    val userId = dataStoreManager.getUserId().asLiveData(Dispatchers.IO)
    var message= mutableStateOf("")
    var showToast = mutableStateOf(false)

    fun obtainDeviceToken (customerToken:String, customerId:String) {
        viewModelScope.launch {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                val token = task.result
                updateUserNotificationToken(customerToken, customerId, token.toString())
            })
        }
    }

    private fun updateUserNotificationToken (customerToken:String, customerId:String, token:String) {
        println("called ")
        showToast.value = false
        message.value = ""

        viewModelScope.launch {
            val appService = AppService.getInstance()

            try {
                val response = appService.updateCustomer(
                    UpdateBody(token),
                    customerId,
                    customerToken,
                    Utils.contentType
                )

                println(response.raw())
                if(response.isSuccessful) {
                     println("Customer notification token added")
                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), UpdateCustomerResponseBody::class.java)
                    }
                }
            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
            }
        }
    }
}

