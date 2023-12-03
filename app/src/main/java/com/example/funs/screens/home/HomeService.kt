package com.example.funs.screens.home


import com.example.funs.utils.Utils
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface HomeService  {
    @GET("read/services?sample=all")
    suspend fun getSampleServices(
        @Header("Authorization") authToken: String?,
        @Header("Content-Type") contentType: String
    ): Response<SampleCleaningServiceResponse>


    @GET("read/services")
    suspend fun getShopServices(
        @Query("openShopsServices") shopId: String?,
        @Header("Authorization") authToken: String?,
        @Header("Content-Type") contentType: String
    ): Response<SampleCleaningServiceResponse>


    @POST("read/orders")
    suspend fun getCustomerOrders(
        @Body filters: OrderFilters,
        @Query("customer") customerId: String?,
        @Header("Authorization") authToken: String?,
        @Header("Content-Type") contentType: String
    ): Response<CustomerOrderResponse>


    companion object {
        var getSampleServiceRequest: HomeService? =  null

        fun getInstance(): HomeService {
            if(getSampleServiceRequest == null) {
                getSampleServiceRequest = Retrofit.Builder()
                    .baseUrl(Utils.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(HomeService::class.java)
            }

            return getSampleServiceRequest!!
        }
    }
}