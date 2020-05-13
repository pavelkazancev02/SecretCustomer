package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.*

interface FeedbackApiService {

    @GET("/feedback/{id}")
    fun getFeedbackById(
        @Header("Authorization") auth: String,
        @Path("id") id: Int
    ): Single<Feedback>

    @GET("/feedback/shop/{id}")
    fun getPaginatedFeedbackByShopId(
        @Header("Authorization") auth: String,
        @Path("id") id: Int,
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Single<List<Feedback>>

    @GET("/feedback/customer/{id}")
    fun getPaginatedFeedbackByCustomerId(
        @Header("Authorization") auth: String,
        @Path("id") id: Int,
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Single<List<Feedback>>

    @POST("/feedback")
    fun leaveFeedback(
        @Header("Authorization") auth: String,
        @Body feedbackPostData: FeedbackPostData
    ): Single<Unit>

    @DELETE("/feedback/{id}")
    fun deleteFeedback(
        @Header("Authorization") auth: String,
        @Path("id") id: Int
    ): Single<Unit>
}

data class FeedbackPostData(
    @SerializedName("shopId") val shopId: Int,
    @SerializedName("pros") val pros: String,
    @SerializedName("cons") val cons: String,
    @SerializedName("rating") val rating: Int,
    @SerializedName("additionalInfo") val additionalInfo: String?
)