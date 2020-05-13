package com.example.secretcustomer.domain.customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secretcustomer.data.UserApiService
import com.example.secretcustomer.ui.fragments.customer.WithdrawCardInfoFragmentDirections
import com.example.secretcustomer.util.Event
import com.example.secretcustomer.util.NavigationCommand
import com.example.secretcustomer.util.sharedpreferences.SharedPreferencesWrapper
import com.example.secretcustomer.util.textWatchers.LiveDataTextWatcher
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

// Инжектим даггером наш сервис, смотреть папку di: RestServiceModule, ViewModelModule
class WithdrawalViewModel
@Inject constructor(
    val userApiService: UserApiService,
    @Named("secure") val secureSharedPrefs: SharedPreferencesWrapper
) : ViewModel() {
    private val _cardNumber = MutableLiveData<String>()
    val cardNumber: LiveData<String> get() = _cardNumber
    val cardNumberWatcher = LiveDataTextWatcher(_cardNumber)
    private val _expirationDate = MutableLiveData<String>()
    val expirationDate: LiveData<String> get() = _expirationDate
    val expirationDateWatcher = LiveDataTextWatcher(_expirationDate)

    private val _errorId = MutableLiveData<Event<Int>>()
    val error: LiveData<Event<Int>> get() = _errorId

    private val _navigationEvents = MutableLiveData<Event<NavigationCommand>>()
    val navigationEvent: LiveData<Event<NavigationCommand>> get() = _navigationEvents

    private val _showLoadingBar = MutableLiveData<Event<Boolean>>()
    val showLoadingBar: LiveData<Event<Boolean>> get() = _showLoadingBar

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun onWithdrawClick(view: View) {
        // todo
        _navigationEvents.value =
            Event(NavigationCommand.To(WithdrawCardInfoFragmentDirections.actionWithdrawCardInfoFragmentToWithdrawFinishFragment()))
    }

    fun onCancelClick(view: View) {
        // todo
    }

    fun onFinishClick(view: View) {
        // todo
    }
}