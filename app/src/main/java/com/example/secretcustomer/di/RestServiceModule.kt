package com.example.secretcustomer.di

import com.example.secretcustomer.data.RestClient
import dagger.Module
import dagger.Provides

@Module
class RestServiceModule {
    @Provides
    fun provideUserService() = RestClient().userApiService

    @Provides
    fun provideShopApiService() = RestClient().shopApiService

    @Provides
    fun provideFeedbackApiService() = RestClient().feedbackApiService

    @Provides
    fun provideSecretCustomerApiService() = RestClient().secretCustomerApiService
}