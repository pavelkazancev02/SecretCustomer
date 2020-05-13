package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.*

interface SecretCustomerApiService {

    @GET("/secretCustomer/actions/{id}")
    fun getActions(
        @Header("Authorization") auth: String,
        @Path("id") id: Int
    ): Single<List<Action>>

    @GET("/secretCustomer/session/active")
    fun getActiveSession(
        @Header("Authorization") auth: String
    ): Single<Session>

    @GET("/secretCustomer/session/isAvailable/{shopId}")
    fun isAvailable(
        @Header("Authorization") auth: String,
        @Path("shopId") shopId: Int
    ): Single<Boolean>

    @POST("/secretCustomer/actions")
    fun createAction(
        @Header("Authorization") auth: String,
        @Body actionPostData: Action
    ): Single<Unit>

    @POST("/secretCustomer/session")
    fun startSession(
        @Header("Authorization") auth: String,
        @Body shopId: Int
    ): Single<Session>

    @POST("/secretCustomer/session/end/{sessionId}")
    fun endSession(
        @Header("Authorization") auth: String,
        @Path("sessionId") sessionId: Int,
        @Body sessionPostData: SessionPostData
    ): Single<Unit>

    @PUT("/secretCustomer/actions")
    fun updateActions(
        @Header("Authorization") auth: String,
        @Field("id") id: Int,
        @Field("shopId") shopId: Int,
        @Field("action") action: String
    ): Single<List<Action>>

    @PUT("/secretCustomer/session/nextStage/{sessionId}")
    fun nextSessionStage(
        @Header("Authorization") auth: String,
        @Path("sessionId") sessionId: Int
    ): Single<Unit>

    @DELETE("/secretCustomer/actions/{id}")
    fun deleteAction(
        @Header("Authorization") auth: String,
        @Path("id") id: Int
    ): Single<Unit>

    @DELETE("/secretCustomer/actions/all/{shopId}")
    fun deleteAllActions(
        @Header("Authorization") auth: String,
        @Path("shopId") shopId: Int
    ): Single<Unit>
}


data class SessionPostData(
    @SerializedName("shopId") val shopId: Int,
    @SerializedName("pros") val pros: String,
    @SerializedName("cons") val cons: String,
    @SerializedName("rating") val rating: Int,
    @SerializedName("additionalInfo") val additionalInfo: String?
)
