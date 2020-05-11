package com.example.secretcustomer.di

import android.content.Context
import com.kn.petFinder.Application
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideApplication() = Application.APPLICATION

    @Provides
    fun provideContext(): Context = Application.APPLICATION

}