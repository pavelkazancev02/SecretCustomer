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

    var userApiService: UserApiService
    var feedbackApiService: FeedbackApiService
    var shopApiService: ShopApiService
    var secretCustomerApiService: SecretCustomerApiService
    var transactionApiService: TransactionApiService

    init {
        userApiService = retrofit.create(UserApiService::class.java)
        feedbackApiService = retrofit.create(FeedbackApiService::class.java)
        shopApiService = retrofit.create(ShopApiService::class.java)
        secretCustomerApiService = retrofit.create(SecretCustomerApiService::class.java)
        transactionApiService = retrofit.create((TransactionApiService::class.java))
    }
}