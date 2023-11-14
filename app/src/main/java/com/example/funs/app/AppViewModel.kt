package com.example.funs.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.example.funs.utils.DataStoreManager
import kotlinx.coroutines.Dispatchers


class AppViewModel(
    application: Application
): AndroidViewModel(application) {

    private val dataStoreManager: DataStoreManager = DataStoreManager(application)
    val currentUserToken = dataStoreManager.getUserToken().asLiveData(Dispatchers.IO)
    val userId = dataStoreManager.getUserId().asLiveData(Dispatchers.IO)

}