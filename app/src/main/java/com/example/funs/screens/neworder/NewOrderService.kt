package com.example.funs.screens.neworder


import com.example.funs.utils.Utils
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewOrderService {
    @GET("read/shops?allOpenShops=all")
    suspend fun getShops(
        @Header("Authorization") authToken: String?,
        @Header("Content-Type") contentType: String
    ): Response<ShopsResponse>



    @GET("read/shops")
    suspend fun searchShop(
        @Query("searchAllShops") keyword: String?,
        @Header("Authorization") authToken: String?,
        @Header("Content-Type") contentType: String
    ): Response<ShopsResponse>


    companion object {
        var getShopsRequest: NewOrderService? =  null

        fun getInstance(): NewOrderService {
            if(getShopsRequest == null) {
                getShopsRequest = Retrofit.Builder()
                    .baseUrl(Utils.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(NewOrderService::class.java)
            }

            return getShopsRequest!!
        }
    }
}