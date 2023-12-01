package com.example.funs.screens.neworder

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.funs.screens.home.CleaningService
import com.example.funs.screens.home.HomeService
import com.example.funs.screens.home.SampleCleaningServiceResponse
import com.example.funs.utils.DataStoreManager
import com.example.funs.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewOrderViewModel (application: Application): AndroidViewModel(application) {
    private val dataStoreManager: DataStoreManager = DataStoreManager(application)
    var message= mutableStateOf("")
    var showToast = mutableStateOf(false)
    var sampleShops =  mutableListOf(Shop())
    var selectedShop = mutableStateOf("empty")
    var servicesList = MutableLiveData<MutableList<CleaningService>>()
    var selectedService = mutableStateOf("empty")


    fun getSampleShops (customerToken:String) {
        showToast.value = false
        message.value = ""

        viewModelScope.launch {
            val newOrderService = NewOrderService.getInstance()

            try {
                val response = newOrderService.getShops(
                    customerToken, Utils.contentType
                )

                if(response.isSuccessful) {
                    message.value = response.body()?.message.toString()
                    response.body()?.data.also {
                        if (it != null) {
                            sampleShops = it as MutableList<Shop>
                        }
                    }

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), ShopsResponse::class.java)
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

    fun searchShops (customerToken:String, keyword:String) {
        showToast.value = false
        message.value = ""

        viewModelScope.launch {
            val newOrderService = NewOrderService.getInstance()

            try {
                val response = newOrderService.searchShop(
                    keyword, customerToken, Utils.contentType
                )

                if(response.isSuccessful) {
                    message.value = response.body()?.message.toString()
                    response.body()?.data.also {
                        if (it != null) {
                            sampleShops = it as MutableList<Shop>
                        }
                    }

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), ShopsResponse::class.java)
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

    fun selectShop (clickedShop:String) {
        viewModelScope.launch {
            selectedShop.value = clickedShop
        }
    }
    fun selectService (clickedService:String) {
        viewModelScope.launch {
            selectedService.value = clickedService
        }
    }

    fun getShopServices (customerToken:String, shopId:String) {
        showToast.value = false
        message.value = ""

        viewModelScope.launch {
            val homeService = HomeService.getInstance()

            try {
                val response = homeService.getShopServices(
                    selectedShop.value, customerToken, Utils.contentType
                )

                showToast.value = true
                if(response.isSuccessful) {
                    message.value = response.body()?.message.toString()

                    response.body()?.data?.let {
                        servicesList.value = it
                    }

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), SampleCleaningServiceResponse::class.java)
                    }
                    message.value =  error?.message.toString()
                }
            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
            }
        }
    }
}