package com.example.funs.screens.notifications

import com.example.funs.screens.home.HomeService
import com.example.funs.screens.home.SampleCleaningServiceResponse
import com.example.funs.utils.Utils
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface NotificationService {
    @PATCH("update/notifications")
    suspend fun markAsRead(
        @Query("id") id: String?,
        @Header("Authorization") authToken: String?,
        @Header("Content-Type") contentType: String
    ): Response<MarkAsReadresponse>

    @GET("read/notifications")
    suspend fun getNotifications(
        @Query("customerId") customerId: String?,
        @Header("Authorization") authToken: String?,
        @Header("Content-Type") contentType: String
    ): Response<NotificationResponse>

    @GET("read/notifications")
    suspend fun getUnreadNotifications(
        @Query("unReadNotifications") customerId: String?,
        @Header("Authorization") authToken: String?,
        @Header("Content-Type") contentType: String
    ): Response<UnreadNotificationResponse>

    companion object {
        var notificationRequest: NotificationService? =  null

        fun getInstance(): NotificationService {
            if(notificationRequest == null) {
                notificationRequest = Retrofit.Builder()
                    .baseUrl(Utils.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(NotificationService::class.java)
            }
            return notificationRequest!!
        }
    }
}