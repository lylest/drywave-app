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

data class UserModel(
    var name: String = "",
    var sex: String = "Male",
    var phoneNumber: String = "",
    var email: String = "",
    var address: String = " ",
    var password: String = "",
    var shop: String = "6523189a22667a9dc91c7308",
    var createdDate:String = "11-14-2023",
    var tmpPassword: String  ="default"
)

data class  SignupResponseBody (
     val message:String = "Failed to Signup",
     val error: String = ""
)



