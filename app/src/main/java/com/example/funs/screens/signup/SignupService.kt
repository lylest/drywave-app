package com.example.funs.screens.signup

import com.example.funs.utils.Utils
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SignupService {

    @POST("create/customers")
    suspend fun registerCustomer(
        @Header("Authorization") authToken: String,
        @Header("Content-Type") contentType: String,
        @Body userModel: UserModel
    ): Response<SignupResponseBody>

    companion object {
        var SignupRequest: SignupService? = null

        fun getInstance(): SignupService {
            if (SignupRequest == null) {
                SignupRequest = Retrofit.Builder()
                    .baseUrl(Utils.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(SignupService::class.java)
            }

            return SignupRequest!!
        }
    }
}