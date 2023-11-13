package com.example.funs.screens.login

sealed class UIEvent {
    data class phoneChanged(val phone: String) : UIEvent()
    data class passwordChanged(val password: String) : UIEvent()

    object LoginButtonClicked : UIEvent()
}