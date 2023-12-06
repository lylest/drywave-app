package com.example.funs.screens.notifications

data class Notification (
    val _id:String,
    val customer: String,
    val order: String,
    val title: String,
    val body: String,
    val isRead: Boolean = false,
    val createdAt: String
)

data class  NotificationResponse (
    val message: String,
    val data: MutableList<Notification>
)

data class MarkAsReadresponse (
    val message: String
)

data class UnreadNotificationResponse (
    val message: String,
    val count: Int
)