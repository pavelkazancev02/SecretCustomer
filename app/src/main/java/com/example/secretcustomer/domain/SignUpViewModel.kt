package com.example.secretcustomer.domain

import android.telephony.PhoneNumberUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secretcustomer.R
import com.example.secretcustomer.data.CreateUserPostData
import com.example.secretcustomer.data.UserApiService
import com.example.secretcustomer.util.Event
import com.example.secretcustomer.util.NavigationCommand
import com.example.secretcustomer.util.constants.LoginConstants
import com.example.secretcustomer.util.textWatchers.LiveDataTextWatcher
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.HttpURLConnection
import javax.inject.Inject

class SignUpViewModel
@Inject constructor(val userApiService: UserApiService) : ViewModel() {
    private val _firstName = MutableLiveData<String>()
    val firstName: LiveData<String> get() = _firstName
    val firstNameWatcher = LiveDataTextWatcher(_firstName)
    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> get() = _lastName
    val lastNameWatcher = LiveDataTextWatcher(_lastName)
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email
    val emailWatcher = LiveDataTextWatcher(_email)
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password
    val passwordWatcher = LiveDataTextWatcher(_password)
    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> get() = _phone
    val phoneWatcher = LiveDataTextWatcher(_phone)

    private val _errorId = MutableLiveData<Event<Int>>()
    val error: LiveData<Event<Int>> get() = _errorId
    private val _navigationEvents = MutableLiveData<Event<NavigationCommand>>()
    val navigationEvent: LiveData<Event<NavigationCommand>> get() = _navigationEvents

    private val _showLoadingBar = MutableLiveData<Event<Boolean>>()
    val showLoadingBar: LiveData<Event<Boolean>> get() = _showLoadingBar
    private val _blockButtons = MutableLiveData<Event<Boolean>>()
    val blockButtons: LiveData<Event<Boolean>> get() = _blockButtons


    private val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun onCreateAccountClick(view: View) {
        when {
            _firstName.value.isNullOrEmpty() ->
                _errorId.value = Event(R.string.no_first_name)
            _lastName.value.isNullOrEmpty() ->
                _errorId.value = Event(R.string.no_last_name)
            _email.value.isNullOrEmpty() -> {
                _errorId.value = Event(R.string.no_email)
            }
            _password.value.isNullOrEmpty() -> {
                _errorId.value = Event(R.string.no_password)
            }
            else -> {
                if (!_phone.value.isNullOrEmpty() && !PhoneNumberUtils.isGlobalPhoneNumber(_phone.value!!)) {
                    _errorId.value = Event(R.string.incorrect_phone_pattern)
                } else {
                    registerUser()
                }
            }
        }
    }

    fun onBackToLoginClick(view: View) {
        _navigationEvents.value = Event(NavigationCommand.Back)
    }

    private fun registerUser() {
        _showLoadingBar.value = Event(true)
        _blockButtons.value = Event(true)
        val userData = CreateUserPostData(
            _firstName.value!!,
            lastName.value!!,
            email.value!!,
            password.value!!,
            phone.value,
            LoginConstants.CUSTOMER_ROLE
        )
        disposables.add(userApiService.createUser(userData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                { error ->
                    _showLoadingBar.postValue(Event(false))
                    _blockButtons.postValue(Event(false))
                    Log.e("Login error", error.message)
                    when (error) {
                        is HttpException ->
                            if (error.code() == HttpURLConnection.HTTP_CONFLICT) {
                                _errorId.postValue(Event(R.string.user_already_exists_error))
                            }
                        else -> _errorId.postValue(Event(R.string.server_not_responding))
                    }
                    _password.postValue("")
                },
                {
                    _showLoadingBar.postValue(Event(false))
                    _blockButtons.postValue(Event(false))
                    _errorId.postValue(Event(R.string.successful_registration))
                    _navigationEvents.postValue(Event(NavigationCommand.Back))
                }
            )
        )
    }
}