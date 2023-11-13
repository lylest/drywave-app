package com.example.funs.screens.signup

object Validators {

    fun validateFullName(fullName:String): ValidateResult {
        return ValidateResult(
            (fullName.isNullOrEmpty() || fullName.length < 3)
        )
    }

    fun validatePhone(phone:String): ValidateResult {
        return ValidateResult(
            (phone.isNullOrEmpty() || phone.length <= 12)
        )
    }

    fun validateEmail(email:String): ValidateResult {
        return ValidateResult(
            (email.isNullOrEmpty() || email.length < 10)
        )
    }

    fun validatePassword(password:String): ValidateResult {
        return ValidateResult(
            (password.isNullOrEmpty() || password.length < 6)
        )
    }

    fun validateSex(sex:String): ValidateResult {
        return ValidateResult(
            (sex.isNullOrEmpty() || sex.length < 3)
        )
    }

    fun validateAddress(address: String): ValidateResult {
        return ValidateResult(
            (address.isNullOrEmpty() || address.length < 4)
        )
    }
}

data class ValidateResult (
    val status: Boolean = false
)

