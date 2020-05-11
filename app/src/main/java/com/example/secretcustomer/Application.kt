package com.kn.petFinder

import android.app.Application
import com.example.secretcustomer.di.DaggerApplicationComponent

class Application: Application() {

    val appComponent = DaggerApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()
        APPLICATION = this
    }

    companion object {
        lateinit var APPLICATION: Application
    }
}