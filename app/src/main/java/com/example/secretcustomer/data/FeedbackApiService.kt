package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.*

interface FeedbackApiService {

    @GET("/feedback/{id}")
    fun getFeedbackById(
        @Path("id") id: Int
    ): Single<Feedback>

    @GET("/feedback/shop/{id}")
    fun getPaginatedFeedbackByShopId(
        @Path("id") id: Int,
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Single<Feedback>

    @GET("/feedback/customer/{id}")
    fun getPaginatedFeedbackByCustomerId(
        @Path("id") id: Int,
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Single<Feedback>

    @POST("/feedback")
    fun leaveFeedback(
        @Body feedbackPostData: FeedbackPostData
    )

    @DELETE("/feedback/{id}")
    fun deleteFeedback(
        @Path("id") id: Int
    )
}

data class FeedbackPostData(
    val customerEmail: String,
    @SerializedName("shopId") val shopId: Int,
    @SerializedName("pros") val pros: String,
    @SerializedName("cons") val cons: String,
    @SerializedName("additionalInfo") val additionalInfo: String?
)