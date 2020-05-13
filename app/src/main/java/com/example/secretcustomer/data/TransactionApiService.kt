package com.example.secretcustomer.data

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TransactionApiService {
    @POST("/balance/user/increase")
    fun increaseUserBalance(
        @Header("Authorization") auth: String,
        @Body userBalancePostData: Transaction
    ): Single<UserDetails>

    @POST("/balance/shop/increase")
    fun increaseShopBalance(
        @Header("Authorization") auth: String,
        @Body userBalancePostData: Transaction
    ): Single<Shop>
}