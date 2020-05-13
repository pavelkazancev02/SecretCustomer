package com.example.secretcustomer.domain.customer

import android.app.Application
import android.content.Intent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secretcustomer.data.UserApiService
import com.example.secretcustomer.ui.activities.LoginActivity
import com.example.secretcustomer.ui.activities.WithdrawActivity
import com.example.secretcustomer.util.Event
import com.example.secretcustomer.util.NavigationCommand
import com.example.secretcustomer.util.constants.LoginConstants
import com.example.secretcustomer.util.sharedpreferences.SharedPreferencesWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

// Инжектим даггером наш сервис, смотреть папку di: RestServiceModule, ViewModelModule
class ProfileViewModel
@Inject constructor(
    val userApiService: UserApiService,
    val application: Application,
    @Named("secure") val secureSharedPrefs: SharedPreferencesWrapper
) : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> get() = _phone

    private val _balance = MutableLiveData<Float>()
    val balance: LiveData<Float> get() = _balance

    // LiveData для управления навигацией, так как она может совершаться только из ui.
    private val _navigationEvents = MutableLiveData<Event<NavigationCommand>>()
    val navigationEvent: LiveData<Event<NavigationCommand>> get() = _navigationEvents

    private val _showLoadingBar = MutableLiveData<Event<Boolean>>()
    val showLoadingBar: LiveData<Event<Boolean>> get() = _showLoadingBar

    private val disposables = CompositeDisposable()

    init {
        _showLoadingBar.postValue(Event(true))
        secureSharedPrefs.getString(LoginConstants.TOKEN)?.let { token ->
            disposables.add(userApiService.getUserInfo(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    {},
                    { response ->
                        _showLoadingBar.postValue(Event(false))
                        _username.value = "${response.firstName} ${response.lastName}"
                        _email.postValue(response.email)
                        _phone.postValue(response.phone)
                        _balance.postValue(response.balance)
                    }
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun onWithdrawAll(view: View) {
        val intent = Intent(application, WithdrawActivity::class.java)
        _navigationEvents.postValue(Event(NavigationCommand.ToIntent(intent)))
    }

    fun onLogout(view: View) {
        secureSharedPrefs.clear(LoginConstants.TOKEN)
        val intent = Intent(application, LoginActivity::class.java)
        _navigationEvents.postValue(Event(NavigationCommand.ToIntentWithFinish(intent)))
    }
}