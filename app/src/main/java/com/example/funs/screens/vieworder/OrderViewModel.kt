package com.example.funs.screens.vieworder

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.funs.screens.home.HomeService
import com.example.funs.screens.home.SampleCleaningServiceResponse
import com.example.funs.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    var order = mutableStateOf<OrderDetails?>(null)
    var message = mutableStateOf("")
    var showToast = mutableStateOf(false)
    var refreshing = mutableStateOf(false)


    fun getOrder(orderId: String, customerToken: String) {
        showToast.value = false
        message.value = ""
        refreshing.value = true

        viewModelScope.launch {
            val orderService = OrderService.getInstance()

            try {
                val response = orderService.getOrderDetails(orderId, customerToken, Utils.contentType)
                println(response.raw())
                if (response.isSuccessful) {
                    refreshing.value = false
                    order.value = response.body()?.data!!
                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), SampleCleaningServiceResponse::class.java)
                    }
                    refreshing.value = false
                    showToast.value = true
                    message.value = error?.message.toString()
                }
            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
                refreshing.value = false
            }
        }
    }

}