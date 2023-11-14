package com.example.funs.screens.signup

object Validators {

    fun validateFullName(fullName:String): ValidateResult {
        return ValidateResult(
            (!fullName.isNullOrEmpty() && fullName.length >= 3)
        )
    }

    fun validatePhone(phone:String): ValidateResult {
        return ValidateResult(
            (!phone.isNullOrEmpty() && phone.length >= 11)
        )
    }

    fun validateEmail(email:String): ValidateResult {
        return ValidateResult(
            (!email.isNullOrEmpty() && email.length >= 6)
        )
    }

    fun validatePassword(password:String): ValidateResult {
        return ValidateResult(
            (!password.isNullOrEmpty() && password.length >= 6)
        )
    }


    fun validateAddress(address: String): ValidateResult {
        return ValidateResult(
            (!address.isNullOrEmpty() && address.length > 4)
        )
    }
}

data class ValidateResult (
    val status: Boolean = false
)

