package com.example.funs.screens.signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.funs.screens.login.LoginViewModel

class SignupViewModel : ViewModel() {
    private val TAG = LoginViewModel::class.simpleName
    var SignupUIState = mutableStateOf(SignupUIState())

    fun onEvent(event: SignupEvent) {

        when (event) {
            is SignupEvent.fullNameChanged -> {
                SignupUIState.value = SignupUIState.value.copy(
                    fullName = event.fullName
                )
                validateData()
            }

            is SignupEvent.sexChanged -> {
                SignupUIState.value = SignupUIState.value.copy(
                    sex = event.sex
                )
                validateData()

            }

            is SignupEvent.phoneChanged -> {
                SignupUIState.value = SignupUIState.value.copy(
                    phone = event.phone
                )
                validateData()
            }

            is SignupEvent.emailChanged -> {
                SignupUIState.value = SignupUIState.value.copy(
                    email = event.email
                )
                validateData()
            }

            is SignupEvent.addressChanged -> {
                SignupUIState.value = SignupUIState.value.copy(
                    address = event.address
                )
                validateData()
            }

            is SignupEvent.passwordChanged -> {
                SignupUIState.value = SignupUIState.value.copy(
                    password = event.password
                )
                validateData()
            }

            is SignupEvent.SignupButtonClicked -> {
                signUpFunction()
            }
        }
    }

    fun signUpFunction() {
        validateData()
    }

    private fun validateData() {
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
            address =  SignupUIState.value.address
        )

        val sexResult = Validators.validateSex(
            sex = SignupUIState.value.sex
        )

        val passwordResult = Validators.validatePassword(
            password =  SignupUIState.value.password
        )

        Log.d(TAG, fullNameResult.toString())
        Log.d(TAG,  SignupUIState.value.fullName)

        SignupUIState.value = SignupUIState.value.copy(
            fullNameError = fullNameResult.status,
            phoneError =  phoneResult.status,
            emailError =  emailResult.status,
            addressError = addressResult.status,
            passwordError = passwordResult.status,
            sexError =  sexResult.status
        )
    }


}