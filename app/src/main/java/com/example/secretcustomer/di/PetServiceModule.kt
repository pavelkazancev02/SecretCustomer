package com.example.secretcustomer.di

import com.example.secretcustomer.data.RestClient
import dagger.Module
import dagger.Provides

@Module
class PetServiceModule {
    @Provides
    fun providePetService() = RestClient()
}