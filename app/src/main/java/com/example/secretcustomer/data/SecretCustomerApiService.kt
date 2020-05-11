package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface SecretCustomerApiService {

    @GET("/secretCustomer/actions{id}")
    fun getActions(
        @Query("id") id: Int
    ): Single<SecretCustomer>

    @GET("/secretCustomer/session/active")
    fun getActiveSession(): Call<SecretCustomer>

    @GET("/secretCustomer/session/isAvailable/{shopId}")
    fun isAvailable(
        @Query("shopId") shopId: Int
    ): Single<SecretCustomer>

    @POST("/secretCustomer/actions")
    fun createAction(
        @Body actionPostData: ActionPostData
    ): Single<SecretCustomer>

    @POST("/secretCustomer/session")
    fun startSession(
        @Body shopId: Int
    ): Single<SecretCustomer>

    @POST("/secretCustomer/session/end/{sessionId}")
    fun endSession(
        @Body sessionPostData: SessionPostData
    ): Single<SecretCustomer>

    @PUT("/secretCustomer/actions")
    fun updateActions(
        @Field("id") id: Int,
        @Field("shopId") shopId: Int,
        @Field("action") action: String
    ): Single<SecretCustomer>

    @PUT("/secretCustomer/session/nextStage/{sessionId}")
    fun nextSessionStage(
        @Path("sessionId") sessionId: Int
    ): Single<SecretCustomer>

    @DELETE("/secretCustomer/actions/{id}")
    fun deleteAction(
        @Path("id") id: Int
    ): Single<SecretCustomer>

    @DELETE("/secretCustomer/actions/all/{shopId}")
    fun deleteAllActions(
        @Path("shopId") shopId: Int
    ): Single<SecretCustomer>
}

data class ActionPostData(
    @SerializedName("id") val id: Int,
    @SerializedName("shopId") val shopId: Int,
    @SerializedName("action") val action: String
)

data class SessionPostData(
    @SerializedName("sessionId") val sessionId: Int,
    @SerializedName("shopId") val shopId: Int,
    @SerializedName("pros") val pros: String,
    @SerializedName("cons") val cons: String,
    @SerializedName("additionalInfo") val additionalInfo: String?
)