package com.example.secretcustomer.domain.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secretcustomer.data.Feedback
import com.example.secretcustomer.data.FeedbackApiService
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
class FeedbackViewModel
@Inject constructor(
    val feedbackApiService: FeedbackApiService,
    @Named("secure") val secureSharedPrefs: SharedPreferencesWrapper
) : ViewModel() {
    private val _feedback = MutableLiveData<Event<List<Feedback>>>()
    val feedback: LiveData<Event<List<Feedback>>> get() = _feedback

    // LiveData для управления навигацией, так как она может совершаться только из ui.
    private val _navigationEvents = MutableLiveData<Event<NavigationCommand>>()
    val navigationEvent: LiveData<Event<NavigationCommand>> get() = _navigationEvents

    private val _showLoadingBar = MutableLiveData<Event<Boolean>>()
    val showLoadingBar: LiveData<Event<Boolean>> get() = _showLoadingBar

    private val disposables = CompositeDisposable()

    init {
        _showLoadingBar.postValue(Event(true))
        secureSharedPrefs.getString(LoginConstants.TOKEN)?.let { token ->
            secureSharedPrefs.getInt(LoginConstants.USER_ID).let { userId ->
                // ToDo pagination
                disposables.add(
                    feedbackApiService.getPaginatedFeedbackByCustomerId(token, userId, 10, 0)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                            {},
                            { response ->
                                _showLoadingBar.postValue(Event(false))
                                _feedback.postValue(Event(response))
                            }
                    )
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}