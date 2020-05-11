package com.example.secretcustomer.di

import android.content.Context
import com.example.secretcustomer.SecretCustomerApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideApplication() = SecretCustomerApplication.APPLICATION

    @Provides
    fun provideContext(): Context = SecretCustomerApplication.APPLICATION

}