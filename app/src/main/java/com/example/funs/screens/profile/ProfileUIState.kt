package com.example.funs.screens.profile

import com.example.funs.utils.CurrentUser

data class  ProfileDetailsResponseBody (
     val message: String = "",
     val data: CurrentUser
)