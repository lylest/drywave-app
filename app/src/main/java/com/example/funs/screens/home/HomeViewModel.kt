package com.example.funs.screens.home

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.funs.utils.DataStoreManager
import com.example.funs.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val dataStoreManager: DataStoreManager = DataStoreManager(application)
    var message= mutableStateOf("")
    var showToast = mutableStateOf(false)
    var sampleServices = MutableLiveData<MutableList<CleaningService>>()
    var customerOrders = MutableLiveData<MutableList<OrderItem>>()
    var refreshing =  mutableStateOf(false)

    fun getSampleServices (customerToken:String) {
        showToast.value = false
        message.value = ""

        viewModelScope.launch {
            val homeService = HomeService.getInstance()

            try {
                val response = homeService.getSampleServices(
                   customerToken, Utils.contentType
                )


                if(response.isSuccessful) {
                    message.value = response.body()?.message.toString()
                    response.body()?.data?.let {
                        sampleServices.value = it
                    }

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), SampleCleaningServiceResponse::class.java)
                    }
                    showToast.value = true
                    message.value =  error?.message.toString()
                }
            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
            }
        }
    }

    private fun getDateThreeMonthsAgo(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -3) // Subtracting 3 months

        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun getTomorrowDate(): String {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, 1)

        val tomorrowDate = currentDate.time
        return dateFormat.format(tomorrowDate)
    }


    fun formatDateString(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return try {
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            "Invalid Date"
        }
    }


    fun getCustomerOrders (customerToken:String, customerId: String) {
        showToast.value = false
        message.value = ""
        refreshing.value = true

        viewModelScope.launch {
            val homeService = HomeService.getInstance()

            try {
                val response = homeService.getCustomerOrders(
                    OrderFilters(
                        fromDate =  getDateThreeMonthsAgo() ,
                        toDate = getTomorrowDate(),
                        orderStatus = "all",
                        paymentStatus = "all"
                    ),
                    customerId,
                    customerToken,
                    Utils.contentType
                )

                if(response.isSuccessful) {
                    response.body()?.data?.let {
                        customerOrders.value = it
                    }
                    refreshing.value = false

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), SampleCleaningServiceResponse::class.java)
                    }
                }
            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
                refreshing.value = false
            }
        }
    }
}