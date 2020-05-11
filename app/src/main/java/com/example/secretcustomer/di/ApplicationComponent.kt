package com.example.secretcustomer.di


import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        SharedPrefsModule::class,
        PetServiceModule::class,
        ViewModelModule::class]
)
interface ApplicationComponent {
}