package com.example.secretcustomer

import android.app.Application
import com.example.secretcustomer.di.ApplicationComponent
import com.example.secretcustomer.di.DaggerApplicationComponent

class SecretCustomerApplication: Application() {

    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()
        APPLICATION = this
    }

    companion object {
        lateinit var APPLICATION: Application
    }
}