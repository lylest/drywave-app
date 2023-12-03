package com.example.funs.screens.home

data class OrderItem(
    var _id: String,
    var dateReceived: String,
    var orderStatus: String,
    var paymentStatus: String,
    var progress: String,
    var readyDate: String,
    var tagId: Int,
    var totalPieces: Int,
    var trackingId: String,
    var percentage: Float,
    var color:String,
    var totalCost:Int
)


data class  CustomerOrderResponse (
    val message: String,
    val data:MutableList<OrderItem>
)

