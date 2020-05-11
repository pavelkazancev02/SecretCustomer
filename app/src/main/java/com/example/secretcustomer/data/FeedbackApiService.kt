package com.example.secretcustomer.data

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface FeedbackApiService {

    @GET("/feedback/{id}")
    fun getFeedbackById(
        @Query("id") id: Int
    ): Call<Feedback>

    @GET("/feedback/shop/{id}")
    fun getPaginatedFeedbackByShopId(
        @Query("id") id: Int,
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Call<Feedback>

    @GET("/feedback/customer/{id}")
    fun getPaginatedFeedbackByCustomerId(
        @Query("id") id: Int,
        @Query("pageSize") pageSize: Int,
        @Query("offset") offset: Int
    ): Call<Feedback>

    @POST("/feedback")
    fun leaveFeedback(
        @Body feedbackPostData: FeedbackPostData
    ) : Call<Feedback>

    @DELETE("/feedback/{id}")
    fun deleteFeedback(
        @Path("id") id: Int
    ) : Call<Feedback>
}

data class FeedbackPostData(
    val customerEmail: String,
    @SerializedName("shopId") val shopId: Int,
    @SerializedName("pros") val pros: String,
    @SerializedName("cons") val cons: String,
    @SerializedName("additionalInfo") val additionalInfo: String?
)