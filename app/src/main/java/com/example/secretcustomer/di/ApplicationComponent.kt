package com.example.secretcustomer.di


import com.example.secretcustomer.ui.fragments.LoginFragment
import com.example.secretcustomer.ui.fragments.ShopsFragment
import com.example.secretcustomer.ui.fragments.SignUpFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        SharedPrefsModule::class,
        RestServiceModule::class,
        ViewModelModule::class]
)
interface ApplicationComponent {
    fun injectLoginFragment(fragment: LoginFragment)
    fun injectSignUpFragment(fragment: SignUpFragment)
    fun injectShopsFragment(fragment: ShopsFragment)
}