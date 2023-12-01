package com.example.funs.screens.neworder

import com.example.funs.screens.home.CleaningService
import com.example.funs.screens.home.Icon

data class  ShopsResponse (
    var message: String = "",
    var data:  List<Shop> = listOf()
)

data class Shop (
    var _id: String= "",
    var name: String = "",
    var logo: Icon = Icon()
)

data class ItemsResponse (

)

