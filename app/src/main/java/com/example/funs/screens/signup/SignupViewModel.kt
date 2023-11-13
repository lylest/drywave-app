package com.example.funs.screens.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SignupViewModel: ViewModel () {
     var SignupUIState = mutableStateOf(SignupUIState())

     fun onEvent(event: SignupEvent) {
          when(event) {
               is SignupEvent.fullNameChanged -> {
                    SignupUIState.value = SignupUIState.value.copy(
                         fullName =  event.fullName
                    )
               }

               is SignupEvent.sexChanged -> {
                    SignupUIState.value =  SignupUIState.value.copy(
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
          }
     }
}