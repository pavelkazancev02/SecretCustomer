package com.example.secretcustomer.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TransactionApiService {
    @POST("/balance/user/increase")
    fun increaseUserBalance(
        @Header("Authorization") auth: String,
        @Body userBalancePostData: Transaction
    ): Call<UserDetails>

    @POST("/balance/user/increase")
    fun increaseShopBalance(
        @Header("Authorization") auth: String,
        @Body userBalancePostData: Transaction
    ): Call<Shop>
}