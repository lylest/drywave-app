package com.example.funs.screens.home

data class OrderFilters(
    var fromDate: String,
    var orderStatus: String,
    var paymentStatus: String,
    var toDate: String
)