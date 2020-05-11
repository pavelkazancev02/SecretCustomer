package com.example.secretcustomer.domain

import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secretcustomer.R
import com.example.secretcustomer.data.CreateUserPostData
import com.example.secretcustomer.data.UserApiService
import com.example.secretcustomer.util.constants.LoginConstants
import com.example.secretcustomer.util.textWatchers.LiveDataTextWatcher
import com.example.secretcustomer.util.Event
import com.example.secretcustomer.util.NavigationCommand
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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
            !_phone.value.isNullOrEmpty() ->
                if (!Patterns.PHONE.matcher(phone.value!!).find())
                    _errorId.value = Event(R.string.incorrect_phone_pattern)
            else -> {
                registerUser()
            }
        }
    }

    fun onBackToLoginClick(view: View) {
        _navigationEvents.value = Event(NavigationCommand.Back)
    }

    private fun registerUser() {
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
            .subscribe(
                { users ->
                    //TODO сделать как будет норм апи
                },
                { error ->
                    Log.e("Login error", error.message)
                    when(error) {
                        is HttpException ->
                            if (error.code() == HttpURLConnection.HTTP_CONFLICT) {
                                _errorId.postValue(Event(R.string.user_already_exists_error))
                            } else if (error.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                                _errorId.postValue(Event(R.string.server_not_responding))
                            }
                    }
                }
            )
        )
    }
}