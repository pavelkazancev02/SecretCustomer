package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface SecretCustomerApiService {

    @GET("/secretCustomer/actions{id}")
    fun getActions(
        @Path("id") id: Int
    ): Single<Action>

    @GET("/secretCustomer/session/active")
    fun getActiveSession(): Call<Session>

    @GET("/secretCustomer/session/isAvailable/{shopId}")
    fun isAvailable(
        @Path("shopId") shopId: Int
    ): Single<Availability>

    @POST("/secretCustomer/actions")
    fun createAction(
        @Body actionPostData: Action
    )

    @POST("/secretCustomer/session")
    fun startSession(
        @Body shopId: Int
    ): Single<Session>

    @POST("/secretCustomer/session/end/{sessionId}")
    fun endSession(
        @Body sessionPostData: SessionPostData
    )

    @PUT("/secretCustomer/actions")
    fun updateActions(
        @Field("id") id: Int,
        @Field("shopId") shopId: Int,
        @Field("action") action: String
    ): Single<Action>

    @PUT("/secretCustomer/session/nextStage/{sessionId}")
    fun nextSessionStage(
        @Path("sessionId") sessionId: Int
    )

    @DELETE("/secretCustomer/actions/{id}")
    fun deleteAction(
        @Path("id") id: Int
    )

    @DELETE("/secretCustomer/actions/all/{shopId}")
    fun deleteAllActions(
        @Path("shopId") shopId: Int
    )
}


data class SessionPostData(
    @SerializedName("shopId") val shopId: Int,
    @SerializedName("pros") val pros: String,
    @SerializedName("cons") val cons: String,
    @SerializedName("additionalInfo") val additionalInfo: String?
)

data class Availability(
    val available: Boolean
)