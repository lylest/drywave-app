package com.example.funs.screens.login

import com.example.funs.screens.signup.ValidateResult

object LoginValidators {

    fun validatePhone(phone:String): ValidateResult {
        return ValidateResult(
            (!phone.isNullOrEmpty() && phone.length >= 11)
        )
    }

    fun validatePassword(password:String): ValidateResult {
        return ValidateResult(
            (!password.isNullOrEmpty() && password.length >= 6)
        )
    }

}

