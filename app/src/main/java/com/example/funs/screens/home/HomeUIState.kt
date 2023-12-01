package com.example.funs.screens.home

import com.example.funs.utils.CurrentUser

data class SampleCleaningServiceResponse(
    var message: String = "",
    var data: MutableList<CleaningService>
)

data class CleaningService(
    var _id:  String = "",
    var code: String = "",
    var shop: String = "",
    var name: String = "",
    var icon: Icon = Icon()
)

data class Icon(
    val _id: String = "",
    val path: String = ""
)