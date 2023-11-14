package com.example.funs.screens.login


import com.example.funs.utils.Utils
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface  LoginService {

    @POST("read/customers?login=yes")
    suspend fun loginCustomer(
        @Header("Authorization") authToken: String,
        @Header("Content-Type") contentType: String,
        @Body loginUIState: LoginUIState
    ): Response<LoginResponseBody>

    companion object {
        var LoginRequest:LoginService? =  null

        fun getInstance(): LoginService {
            if(LoginRequest == null) {
                LoginRequest = Retrofit.Builder()
                    .baseUrl(Utils.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(LoginService::class.java)
            }

            return LoginRequest!!
        }
    }
}