package com.example.secretcustomer.di


import com.example.secretcustomer.ui.fragments.LoginFragment
import com.example.secretcustomer.ui.fragments.SignUpFragment
import com.example.secretcustomer.ui.fragments.customer.*
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
    fun injectCustomerFeedbackFragment(fragment: MyFeedbackFragment)
    fun injectProfileFragment(fragment: ProfileFragment)
    fun injectProfileWithdrawFragment(fragment: WithdrawCardInfoFragment)
    fun injectProfileWithdrawFinishFragment(fragment: WithdrawFinishFragment)
}