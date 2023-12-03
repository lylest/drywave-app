package com.example.funs.screens.vieworder


import com.example.funs.screens.home.HomeService
import com.example.funs.utils.Utils
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface OrderService {

    @GET("read/orders")
    suspend fun getOrderDetails(
        @Query("customerOrderById") orderId: String?,
        @Header("Authorization") authToken: String?,
        @Header("Content-Type") contentType: String
    ): Response<OrderDetailsResponse>

    companion object {
        var getOrderDetailsRequest: OrderService? = null

        fun getInstance(): OrderService {
            if (getOrderDetailsRequest == null) {
                getOrderDetailsRequest = Retrofit.Builder()
                    .baseUrl(Utils.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(OrderService::class.java)
            }

            return getOrderDetailsRequest!!
        }
    }
}