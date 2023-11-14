package com.example.funs.screens.login

import com.example.funs.utils.CurrentUser

data class LoginUIState (
    var phone: String = "",
    var password: String = "",

    var phoneError: Boolean = false,
    var passwordError:Boolean = false

)

data class LoginResponseBody (
    var message: String = "",
    var user:CurrentUser,
    var token: String = ""
)