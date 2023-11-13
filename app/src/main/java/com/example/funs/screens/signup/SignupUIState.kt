package com.example.funs.screens.signup

data class SignupUIState(
    var fullName: String = "",
    var sex: String = " ",
    var phone: String = "",
    var email: String = "",
    var address: String = " ",
    var password: String = "",

    var fullNameError: Boolean = false,
    var sexError: Boolean = false,
    var phoneError: Boolean = false,
    var emailError: Boolean = false,
    var addressError: Boolean = false,
    var passwordError: Boolean = false

)