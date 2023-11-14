package com.example.funs.screens.profile

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.funs.utils.CurrentUser
import com.example.funs.utils.DataStoreManager
import com.example.funs.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val dataStoreManager: DataStoreManager = DataStoreManager(application)
    var message= mutableStateOf("")
    var showToast = mutableStateOf(false)
    val currentUserToken = dataStoreManager.getUserToken().asLiveData(Dispatchers.IO)
    val userId = dataStoreManager.getUserId().asLiveData(Dispatchers.IO)
    var customer = mutableStateOf(CurrentUser())

    fun getCustomerFunction (customerId:String?, customerToken: String?) {
        showToast.value = false
        message.value = ""

        viewModelScope.launch {
            val profileService = ProfileService.getInstance()

            try {
                val response = profileService.getSpecificCustomer(
                  customerId, customerToken, Utils.contentType
                )

                showToast.value = true
                if(response.isSuccessful) {
                    message.value = response.body()?.message.toString()
                    response.body()?.data.also {
                        if (it != null) {
                            customer.value = it
                        }
                    }

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), ProfileDetailsResponseBody::class.java)
                    }
                    message.value =  error?.message.toString()
                }

            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
            }
        }
    }

    fun logOutFunction () {
        viewModelScope.launch{
            dataStoreManager.setUserId("not-found")
            dataStoreManager.setUserToken("not-found")
        }
    }

}