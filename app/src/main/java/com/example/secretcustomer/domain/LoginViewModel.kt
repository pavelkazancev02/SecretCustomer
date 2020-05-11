package com.example.secretcustomer.domain

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secretcustomer.R
import com.example.secretcustomer.data.LoginPostData
import com.example.secretcustomer.data.UserApiService
import com.example.secretcustomer.ui.fragments.LoginFragmentDirections
import com.example.secretcustomer.util.textWatchers.LiveDataTextWatcher
import com.example.secretcustomer.util.Event
import com.example.secretcustomer.util.NavigationCommand
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

// Инжектим даггером наш сервис, смотреть папку di: RestServiceModule, ViewModelModule
class LoginViewModel
@Inject constructor(val userApiService: UserApiService) : ViewModel() {

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

    private val disposables = CompositeDisposable()

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
        val userData = LoginPostData(_email.value!!, password.value!!)
        disposables.add(userApiService.logUser(userData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { users ->
                    //TODO сделать как будет норм апи
                },
                { error ->
                    // В асинхронищине в LiveData нужно постить значение, а не просто сетить, так как
                    // на другом треде
                    Log.e("Login error", error.message)
                    _errorId.postValue(Event(R.string.wrong_login_data))
                }
            )
        )
    }
}