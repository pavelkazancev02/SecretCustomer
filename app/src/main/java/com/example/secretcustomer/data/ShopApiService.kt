package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface ShopApiService {

    @GET("/shops")
    fun getPaginatedShopList(
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Call<Shop>

    @GET("/shops/{id}")
    fun getShopById(
        @Query("id") id: Int
    ): Call<Shop>

    @GET("/shops/owner/{id}")
    fun getShopsByOwnerId(
        @Query("id") id: Int,
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Call<Shop>

    @POST("/shops")
    fun createShop(
        @Body shopPostData: ShopPostData
    ) : Call<Shop>

    @PUT("/shops/update")
    fun updateShop(
        @Field("name") name: String,
        @Field("ownerId") ownerId: Int,
        @Field("balance") balance: Float,
        @Field("address") address: String
    ) : Call<Shop>

    @DELETE("/shops/{id}")
    fun deleteShop(
        @Path("id") id: Int
    ) : Call<Shop>
}

data class ShopPostData(
    @SerializedName("name") val name: String,
    @SerializedName("ownerId") val ownerId: Int,
    @SerializedName("address") val address: String
)