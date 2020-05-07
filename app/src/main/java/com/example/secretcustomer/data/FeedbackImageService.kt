package com.example.secretcustomer.data

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FeedbackImageService {

    @GET("/feedback/{id}")
    fun getFeedbackById(
        @Query("id") id: Int
    ): Call<Feedback>

    @GET("/feedback/shop/{id}")
    fun getFeedbackByShopId(
        @Query("id") id: Int
    ): Call<Feedback>

    @GET("/feedback/customer/{id}")
    fun getFeedbackByCustomerId(
        @Query("id") id: Int
    ): Call<Feedback>

    @POST("/feedback")
    fun leaveFeedback()

    @DELETE("/feedback/{id}")
    fun deleteFeedback()
}