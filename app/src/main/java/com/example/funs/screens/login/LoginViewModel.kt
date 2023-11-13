package com.example.funs.screens.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private  val TAG = LoginViewModel::class.simpleName
    var LoginUIState = mutableStateOf(LoginUIState())

    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.phoneChanged -> {
                LoginUIState.value = LoginUIState.value.copy(
                    phone = event.phone
                )
                printState()
            }
            is UIEvent.passwordChanged -> {
                LoginUIState.value = LoginUIState.value.copy(
                    password = event.password
                )
                printState()
            }
        }
    }

    private fun printState() {
        Log.d(TAG, LoginUIState.value.toString())
    }
}