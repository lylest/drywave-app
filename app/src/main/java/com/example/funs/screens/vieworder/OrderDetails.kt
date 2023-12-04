package com.example.funs.screens.vieworder

import com.example.funs.screens.neworder.Piece

data class OrderDetails(
    var _id: String,
    var amountPaid: Int,
    var checkoutCode: String,
    var dateReceived: String,
    var defects: List<Defect>,
    var discount: Int,
    var isExpress: Boolean,
    var orderStatus: String,
    var paymentMethod: String,
    var paymentStatus: String,
    var pieces: List<Piece>,
    var progress: String,
    var readyDate: String,
    var service: Service,
    var shop: Shop,
    var tagId: Int,
    var totalCost: Int,
    var totalPieces: Int,
    var trackingId: String
)

data class Defect(
    var name: String,
    var value: String
)

data class Shop(
    var _id: String,
    var address: String,
    var contacts: List<String>,
    var logo: Logo,
    var name: String,
    var tinNumber: String
)

data class Logo(
    var _id: String,
    var path: String
)

data class Service(
    var _id: String,
    var name: String,
    var code: String
)

data class OrderDetailsResponse (
    var message:String,
    var data: OrderDetails
)

data class  UpdateResponse (
    var message: String
)

data class CancelBody (
    var orderStatus: String
)