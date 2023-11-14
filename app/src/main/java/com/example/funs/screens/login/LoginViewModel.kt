package com.example.funs.screens.login


import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.funs.utils.DataStoreManager
import com.example.funs.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.launch

class LoginViewModel(
   application: Application
) : AndroidViewModel( application ) {
    var LoginUIState = mutableStateOf(LoginUIState())
    var disabled = mutableStateOf(true)
    var showToast = mutableStateOf(false)
    var loginSuccess = mutableStateOf(false)
    var loginMessage = mutableStateOf("")
    private val dataStoreManager: DataStoreManager = DataStoreManager(application)

    private fun setCurrentUser(userAuthToken: String = "not-found") {
        viewModelScope.launch {
           dataStoreManager.setUserToken(userAuthToken)
        }
    }

   private fun setUserId(userId: String = "not-found") {
        viewModelScope.launch {
            dataStoreManager.setUserId(userId)
        }
    }

    fun loginFunction () {
        showToast.value = false
        loginSuccess.value = false
        loginMessage.value = ""
        viewModelScope.launch {
            val loginService = LoginService.getInstance()

            try {
                val response = loginService.loginCustomer(
                    Utils.token, Utils.contentType, LoginUIState(
                        phone = LoginUIState.value.phone,
                        password = LoginUIState.value.password
                    )
                )

                showToast.value = true
                if(response.isSuccessful) {
                    loginMessage.value = response.body()?.message.toString()
                    response.body()?.token?.let { setCurrentUser(it) }
                    response.body()?.user?._id.let {
                        if (it != null) {
                            setUserId(it)
                        }
                    }
                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), LoginResponseBody::class.java)
                    }
                    loginMessage.value =  error?.message.toString()
                }

            } catch (e: Exception) {
                loginMessage.value = e.message.toString()
                showToast.value = true
            }
        }
    }



    fun onEvent(event: UIEvent) {
        validateData()
        when (event) {
            is UIEvent.phoneChanged -> {
                LoginUIState.value = LoginUIState.value.copy(
                    phone = event.phone
                )

            }

            is UIEvent.passwordChanged -> {
                LoginUIState.value = LoginUIState.value.copy(
                    password = event.password
                )

            }

            is UIEvent.LoginButtonClicked -> {
                loginFunction()
            }

        }

    }


    private fun validateData() {

        val phoneResult = LoginValidators.validatePhone(
            phone = LoginUIState.value.phone
        )


        val passwordResult = LoginValidators.validatePassword(
            password =  LoginUIState.value.password
        )


        LoginUIState.value = LoginUIState.value.copy(
            phoneError =  phoneResult.status,
            passwordError = passwordResult.status,
        )

        var inputsStatus = phoneResult.status && passwordResult.status
        disabled.value = !inputsStatus
    }


}