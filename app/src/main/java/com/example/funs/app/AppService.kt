package com.example.funs.app

import androidx.lifecycle.LiveData
import com.example.funs.utils.Utils
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Query

interface AppService {

    @PATCH("update/customers")
    suspend fun updateCustomer(
        @Body notificationBody: UpdateBody,
        @Query("id") customerId: String,
        @Header("Authorization") authToken: String?,
        @Header("Content-Type") contentType: String
    ): Response<UpdateCustomerResponseBody>

    companion object {
        var appServiceRequest: AppService? =  null

        fun getInstance(): AppService {
            if(appServiceRequest == null) {
                appServiceRequest = Retrofit.Builder()
                    .baseUrl(Utils.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(AppService::class.java)
            }

            return appServiceRequest!!
        }
    }
}