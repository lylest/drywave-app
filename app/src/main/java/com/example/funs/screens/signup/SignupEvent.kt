package com.example.funs.screens.signup

sealed class SignupEvent {
    data class fullNameChanged(val fullName: String) : SignupEvent()
    data class sexChanged(val sex: String) : SignupEvent()
    data class phoneChanged(val phone: String) : SignupEvent()
    data class emailChanged(val email: String) : SignupEvent()
    data class addressChanged(val address: String) : SignupEvent()
    data class passwordChanged(val password: String) : SignupEvent()

    object SignupButtonClicked: SignupEvent()
}