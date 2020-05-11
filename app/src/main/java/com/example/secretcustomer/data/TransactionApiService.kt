package com.example.secretcustomer.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TransactionApiService{
    @POST("/balance/user/increase")
    fun increaseUserBalance(
        @Body userBalancePostData: Transaction
    ): Call<Transaction>

    @POST("/balance/user/increase")
    fun increaseShopBalance(
        @Body userBalancePostData: Transaction
    ): Call<Transaction>
}