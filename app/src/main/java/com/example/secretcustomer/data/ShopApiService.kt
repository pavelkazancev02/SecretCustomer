package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.*

interface ShopApiService {

    @GET("/shops")
    fun getPaginatedShopList(
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Single<Shop>

    @GET("/shops/{id}")
    fun getShopById(
        @Path("id") id: Int
    ): Single<Shop>

    @GET("/shops/owner/{id}")
    fun getShopsByOwnerId(
        @Path("id") id: Int,
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Single<Shop>

    @POST("/shops")
    fun createShop(
        @Body shopPostData: ShopPostData
    ): Single<Shop>

    @PUT("/shops/update")
    fun updateShop(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("ownerId") ownerId: Int,
        @Field("balance") balance: Float,
        @Field("address") address: String
    ): Single<Shop>

    @DELETE("/shops/{id}")
    fun deleteShop(
        @Path("id") id: Int
    ): Single<Shop>
}

data class ShopPostData(
    @SerializedName("name") val name: String,
    @SerializedName("ownerId") val ownerId: Int,
    @SerializedName("address") val address: String
)