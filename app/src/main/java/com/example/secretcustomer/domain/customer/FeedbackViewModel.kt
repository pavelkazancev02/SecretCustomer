package com.example.secretcustomer.domain.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secretcustomer.data.Feedback
import com.example.secretcustomer.data.FeedbackApiService
import com.example.secretcustomer.util.Event
import com.example.secretcustomer.util.NavigationCommand
import com.example.secretcustomer.util.sharedpreferences.SharedPreferencesWrapper
import io.reactivex.disposables.CompositeDisposable
import java.util.*
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
        _feedback.postValue(
            Event(
                listOf(
                    Feedback(
                        id = 0,
                        shopName = "Test Shop",
                        customerEmail = "text",
                        pros = "",
                        cons = "",
                        additionalInfo = "Some text of feedback",
                        date = Date()
                    ),
                    Feedback(
                        id = 1,
                        shopName = "Test Shop 2",
                        customerEmail = "text",
                        pros = "",
                        cons = "",
                        additionalInfo = "Another Some text of feedback",
                        date = Date()
                    )
                )
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}