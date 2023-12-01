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

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val dataStoreManager: DataStoreManager = DataStoreManager(application)
    var message= mutableStateOf("")
    var showToast = mutableStateOf(false)
    var sampleServices = MutableLiveData<MutableList<CleaningService>>()

    fun getSampleServices (customerToken:String) {
        showToast.value = false
        message.value = ""

        viewModelScope.launch {
            val homeService = HomeService.getInstance()

            try {
                val response = homeService.getSampleServices(
                   customerToken, Utils.contentType
                )

                showToast.value = true
                if(response.isSuccessful) {
                    message.value = response.body()?.message.toString()
                    response.body()?.data?.let {
                        sampleServices.value = it
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