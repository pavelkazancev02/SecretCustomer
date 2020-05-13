package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.*

interface ShopApiService {

    @GET("/shops")
    fun getPaginatedShopList(
        @Header("Authorization") auth: String,
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Single<List<Shop>>

    @GET("/shops/{id}")
    fun getShopById(
        @Header("Authorization") auth: String,
        @Path("id") id: Int
    ): Single<Shop>

    @GET("/shops/owner/{id}")
    fun getShopsByOwnerId(
        @Header("Authorization") auth: String,
        @Path("id") id: Int,
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Single<List<Shop>>

    @POST("/shops")
    fun createShop(
        @Header("Authorization") auth: String,
        @Body shopPostData: ShopPostData
    ): Single<Shop>

    @PUT("/shops/update")
    fun updateShop(
        @Header("Authorization") auth: String,
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("ownerId") ownerId: Int,
        @Field("balance") balance: Float,
        @Field("address") address: String
    ): Single<Shop>

    @DELETE("/shops/{id}")
    fun deleteShop(
        @Header("Authorization") auth: String,
        @Path("id") id: Int
    ): Single<Unit>
}

data class ShopPostData(
    @SerializedName("name") val name: String,
    @SerializedName("ownerId") val ownerId: Int,
    @SerializedName("address") val address: String
)