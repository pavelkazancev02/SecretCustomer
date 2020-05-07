package com.example.secretcustomer.data

import retrofit2.Call
import retrofit2.http.*

interface ShopImageService {

    @GET("/shops")
    fun getShopList(): Call<Shop>

    @GET("/shops/{id}")
    fun getShopById(
        @Query("id") id: Int
    ): Call<Shop>

    @GET("/shops/owner/{id}")
    fun getFeedbackByCustomerId(
        @Query("id") id: Int
    ): Call<Shop>

    @POST("/shops")
    fun createShop()

    @PUT("/shops/update")
    fun updateShop()

    @DELETE("/shops/{id}")
    fun deleteShop()
}