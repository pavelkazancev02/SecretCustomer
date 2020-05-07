package com.example.secretcustomer.data

import com.example.secretcustomer.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {

    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder().apply {
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
    }

    private val retrofit = Retrofit.Builder().apply {
        baseUrl(BuildConfig.BASE_URL)
        client(okHttpBuilder.build())
        addConverterFactory(GsonConverterFactory.create())
    }.build()

    var userImageService: UserImageService
    var feedbackImageService: FeedbackImageService

    init {
        userImageService = retrofit.create(UserImageService::class.java)
        feedbackImageService = retrofit.create(FeedbackImageService::class.java)
    }
}