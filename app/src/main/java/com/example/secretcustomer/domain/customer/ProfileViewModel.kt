package com.example.secretcustomer.domain.customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secretcustomer.data.UserApiService
import com.example.secretcustomer.util.Event
import com.example.secretcustomer.util.NavigationCommand
import com.example.secretcustomer.util.sharedpreferences.SharedPreferencesWrapper
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

// Инжектим даггером наш сервис, смотреть папку di: RestServiceModule, ViewModelModule
class ProfileViewModel
@Inject constructor(
    val userApiService: UserApiService,
    @Named("secure") val secureSharedPrefs: SharedPreferencesWrapper
) : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> get() = _phone

    private val _balance = MutableLiveData<String>()
    val balance: LiveData<String> get() = _balance

    // LiveData для управления навигацией, так как она может совершаться только из ui.
    private val _navigationEvents = MutableLiveData<Event<NavigationCommand>>()
    val navigationEvent: LiveData<Event<NavigationCommand>> get() = _navigationEvents

    private val _showLoadingBar = MutableLiveData<Event<Boolean>>()
    val showLoadingBar: LiveData<Event<Boolean>> get() = _showLoadingBar

    private val disposables = CompositeDisposable()

    init {
        _username.postValue("Andrey Pavlenko")
        _email.postValue("test email")
        _phone.postValue("test phone")
        _balance.postValue("You earned 1 coin")
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun onWithdrawAll(view: View) {
        // todo
    }

    fun onLogout(view: View) {
        // tod
    }
}