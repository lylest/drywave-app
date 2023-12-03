package com.example.funs.screens.neworder

import com.example.funs.screens.home.Icon

data class ShopsResponse(
    var message: String = "",
    var data: List<Shop> = listOf()
)

data class Shop(
    var _id: String = "",
    var name: String = "",
    var logo: Icon = Icon()
)

data class ItemsResponse(
    var message: String = "",
    var data: MutableList<Item>
)

data class Item(
    var _id: String,
    var item: String,
    var pieces: Int,
    var price: Int,
    var shop: String
)

data class SearchItemBody(
    var shopId: String = "",
    var query: String = ""
)

data class Piece(
    var id: String,
    var category: String,
    var categoryPieces: Int,
    var color: Color,
    var description: String,
    var itemTotal: Int,
    var name: String,
    var pieces: Int,
    var quantity: Int,
    var unitPrice: Int
)

data class Color(
    var code: String,
    var id: Int,
    var kisw: String,
    var colorName: String
)

data class Order(
    val customer: String,
    val shop: String,
    val service: String,
    val pieces: MutableList<Piece>,
    val totalCost: Int,
    val discount: Int,
    val amountPaid: Int,
    val defects: List<Inspection> = emptyList(),
    val dateReceived: String,
    val readyDate: String,
    val paymentStatus: String,
    val paymentMethod: String,
    val trackingId: String,
    val checkoutCode: String,
    val isExpress: Boolean,
    val totalPieces: Int,
    val orderStatus: String
)

data class ExtendOrder(
    val _id: String,
    val dateReceived: String,
    val readyDate: String,
    val paymentStatus: String,
    val trackingId: String,
    val orderStatus: String,
    val progress: String
)

data class Inspection(
    val name: String = ""
)

data class NewOrderResponse(
    var message: String,
    var data: ExtendOrder
)

