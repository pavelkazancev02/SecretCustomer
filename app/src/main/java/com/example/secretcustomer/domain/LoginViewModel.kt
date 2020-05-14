package com.example.secretcustomer.domain

import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secretcustomer.R
import com.example.secretcustomer.data.LoginPostData
import com.example.secretcustomer.data.UserApiService
import com.example.secretcustomer.ui.activities.CustomerActivity
import com.example.secretcustomer.ui.fragments.LoginFragmentDirections
import com.example.secretcustomer.util.Event
import com.example.secretcustomer.util.NavigationCommand
import com.example.secretcustomer.util.constants.LoginConstants
import com.example.secretcustomer.util.sharedpreferences.SharedPreferencesWrapper
import com.example.secretcustomer.util.textWatchers.LiveDataTextWatcher
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

// Инжектим даггером наш сервис, смотреть папку di: RestServiceModule, ViewModelModule
class LoginViewModel
@Inject constructor(
    val userApiService: UserApiService,
    val application: Application,
    @Named("secure") val secureSharedPrefs: SharedPreferencesWrapper
) : ViewModel() {

    // Эти LiveData биндятся напрямую в файлы разметки (layout/fragment_login.xml)
    // LiveData хранящие инпут поля и TextWatcher для них, который обновляет данные в LiveData,
    // когда их меняет юзер
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email
    val emailWatcher = LiveDataTextWatcher(_email)
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password
    val passwordWatcher = LiveDataTextWatcher(_password)

    // На две нижние LiveData подписываемся во вью
    // LiveData для любых ошибок. В нее кладется id ресурса из R.strings
    // Id обернут в Event, специальный враппер, который следит позволяет отследить евент
    // только один раз при его изменении. Иначе при повороте экрана у нас все время будут писаться ошибки
    private val _errorId = MutableLiveData<Event<Int>>()
    val error: LiveData<Event<Int>> get() = _errorId

    // LiveData для управления навигацией, так как она может совершаться только из ui.
    private val _navigationEvents = MutableLiveData<Event<NavigationCommand>>()
    val navigationEvent: LiveData<Event<NavigationCommand>> get() = _navigationEvents

    private val _showLoadingBar = MutableLiveData<Event<Boolean>>()
    val showLoadingBar: LiveData<Event<Boolean>> get() = _showLoadingBar
    private val _blockButtons = MutableLiveData<Event<Boolean>>()
    val blockButtons: LiveData<Event<Boolean>> get() = _blockButtons

    private val disposables = CompositeDisposable()

    init {
        val email = secureSharedPrefs.getString(LoginConstants.SAVED_EMAIL)
        val password = secureSharedPrefs.getString(LoginConstants.SAVED_PASSWORD)
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            _email.value = email
            _password.value = password
            loginUser()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun onLoginClick(view: View) {
        when {
            _email.value.isNullOrEmpty() -> {
                _errorId.value = Event(R.string.no_email)
            }
            _password.value.isNullOrEmpty() -> {
                _errorId.value = Event(R.string.no_password)
            }
            else -> {
                loginUser()
            }
        }
    }

    fun onCreateAccountClick(view: View) {
        _navigationEvents.value =
            Event(NavigationCommand.To(LoginFragmentDirections.actionLoginFragmentToSignUpFragment()))
    }

    private fun loginUser() {
        _showLoadingBar.value = Event(true)
        _blockButtons.value = Event(true)
        val userData = LoginPostData(_email.value!!, password.value!!)
        disposables.add(userApiService.logUser(userData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                {},
                { response ->
                    _showLoadingBar.postValue(Event(false))
                    _blockButtons.postValue(Event(false))
                    if (response.isSuccessful) {
                        val token = response.headers().get(LoginConstants.TOKEN_HEADER)
                        secureSharedPrefs.set(LoginConstants.SAVED_EMAIL, _email.value!!)
                        secureSharedPrefs.set(LoginConstants.SAVED_PASSWORD, _password.value!!)
                        secureSharedPrefs.set(LoginConstants.TOKEN, token!!)
                        loadAdditionalInfo(token)
                    } else {
                        // В асинхронищине в LiveData нужно постить значение, а не просто сетить, так как
                        // на другом треде
                        _showLoadingBar.postValue(Event(false))
                        Log.e("Login error", response.message())
                        when (response.code()) {
                            409 -> _errorId.postValue(Event(R.string.wrong_login_data))
                            else -> _errorId.postValue(Event(R.string.server_not_responding))
                        }
                        _password.postValue("")
                    }
                }
            )
        )
    }

    private fun loadAdditionalInfo(token: String) {
        disposables.add(userApiService.getUserInfo(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                {},
                { response ->
                    _showLoadingBar.postValue(Event(false))
                    secureSharedPrefs.set(LoginConstants.ROLE, response.role.toString())
                    secureSharedPrefs.set(LoginConstants.USER_ID, response.id)
                    val intent = Intent(application, CustomerActivity::class.java)
                    _navigationEvents.postValue(
                        Event(
                            NavigationCommand.ToIntentWithFinish(
                                intent
                            )
                        )
                    )
                }
            )
        )
    }
}