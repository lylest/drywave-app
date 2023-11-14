package com.example.funs.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.funs.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    lateinit var navigateTo: (String) -> Unit
    var SignupUIState = mutableStateOf(SignupUIState())
    var signupResponse = mutableStateOf("")
    var disabled = mutableStateOf(true)
    var showToast = mutableStateOf(false)
    var signupSuccess = mutableStateOf(false)

    fun onEvent(event: SignupEvent) {
        validateData()
        when (event) {
            is SignupEvent.fullNameChanged -> {
                SignupUIState.value = SignupUIState.value.copy(
                    fullName = event.fullName
                )
            }

            is SignupEvent.sexChanged -> {
                SignupUIState.value = SignupUIState.value.copy(
                    sex = event.sex
                )
            }

            is SignupEvent.phoneChanged -> {
                SignupUIState.value = SignupUIState.value.copy(
                    phone = event.phone
                )
            }

            is SignupEvent.emailChanged -> {
                SignupUIState.value = SignupUIState.value.copy(
                    email = event.email
                )
            }

            is SignupEvent.addressChanged -> {
                SignupUIState.value = SignupUIState.value.copy(
                    address = event.address
                )
            }

            is SignupEvent.passwordChanged -> {
                SignupUIState.value = SignupUIState.value.copy(
                    password = event.password
                )
            }

            is SignupEvent.SignupButtonClicked -> {
                signUpFunction()
            }
        }
    }


    fun signUpFunction() {
        signupResponse.value = ""
        showToast.value = false
        viewModelScope.launch {
            val signupService = SignupService.getInstance()
            try {
                val responseMessage = signupService.registerCustomer(
                    Utils.token, Utils.contentType, UserModel(
                        name = SignupUIState.value.fullName,
                        phoneNumber = SignupUIState.value.phone,
                        email = SignupUIState.value.email,
                        address = SignupUIState.value.address,
                        password = SignupUIState.value.password,
                        sex = SignupUIState.value.sex
                    )
                )

                showToast.value = true
                if (responseMessage.isSuccessful) {
                    signupResponse.value = responseMessage.body()?.message.toString()
                     signupSuccess.value = true
                } else {
                     val error = responseMessage.errorBody()?. let {
                         Gson().fromJson(it.string(), SignupResponseBody::class.java)
                     }
                    signupResponse.value = error?.message.toString()
                }

            } catch (e: Exception) {
                signupResponse.value = e.message.toString()
                showToast.value = true
            }
        }
    }

    private fun validateData(): Boolean {
        val fullNameResult = Validators.validateFullName(
            fullName = SignupUIState.value.fullName
        )

        val phoneResult = Validators.validatePhone(
            phone = SignupUIState.value.phone
        )

        val emailResult = Validators.validateEmail(
            email = SignupUIState.value.email
        )

        val addressResult = Validators.validateAddress(
            address = SignupUIState.value.address
        )

        val passwordResult = Validators.validatePassword(
            password = SignupUIState.value.password
        )

        SignupUIState.value = SignupUIState.value.copy(
            fullNameError = fullNameResult.status,
            phoneError = phoneResult.status,
            emailError = emailResult.status,
            addressError = addressResult.status,
            passwordError = passwordResult.status,
        )

        var inputsStatus = fullNameResult.status && phoneResult.status && emailResult.status
                && addressResult.status && passwordResult.status

        disabled.value = !inputsStatus
        return inputsStatus


    }


}