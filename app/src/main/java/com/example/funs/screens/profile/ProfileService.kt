package com.example.funs.screens.profile


import com.example.funs.utils.Utils
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileService {
    @GET("read/customers")

    suspend fun getSpecificCustomer(
        @Query("id") customerId: String?,
        @Header("Authorization") authToken: String?,
        @Header("Content-Type") contentType: String
    ): Response<ProfileDetailsResponseBody>

    companion object {
        var getUserRequest: ProfileService? =  null

        fun getInstance(): ProfileService {
            if(getUserRequest == null) {
                getUserRequest = Retrofit.Builder()
                    .baseUrl(Utils.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ProfileService::class.java)
            }

            return getUserRequest!!
        }
    }
}