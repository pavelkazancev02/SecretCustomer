package com.example.secretcustomer.domain.customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secretcustomer.data.UserApiService
import com.example.secretcustomer.util.Event
import com.example.secretcustomer.util.NavigationCommand
import com.example.secretcustomer.util.sharedpreferences.SharedPreferencesWrapper
import com.example.secretcustomer.util.textWatchers.LiveDataTextWatcher
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

// Инжектим даггером наш сервис, смотреть папку di: RestServiceModule, ViewModelModule
class InspectionViewModel
@Inject constructor(
    val userApiService: UserApiService,
    @Named("secure") val secureSharedPrefs: SharedPreferencesWrapper
) : ViewModel() {
    private val _currentStage = MutableLiveData<Stage>()
    val currentStage: LiveData<Stage> get() = _currentStage
    private val _currentStep = MutableLiveData<Int>()
    val currentStep: LiveData<Int> get() = _currentStep
    private val _totalSteps = MutableLiveData<Int>()
    val totalSteps: LiveData<Int> get() = _totalSteps
    private val _shopTitle = MutableLiveData<String>()
    val shopTitle: LiveData<String> get() = _shopTitle
    private val _shopAddress = MutableLiveData<String>()
    val shopAddress: LiveData<String> get() = _shopAddress
    private val _taskText = MutableLiveData<String>()
    val taskText: LiveData<String> get() = _taskText
    private val _rating = MutableLiveData<Rating>()
    val rating: LiveData<Rating> get() = _rating

    private val _taskReport = MutableLiveData<String>()
    val taskReport: LiveData<String> get() = _taskReport
    val taskReportWatcher = LiveDataTextWatcher(_taskReport)

    private val _errorId = MutableLiveData<Event<Int>>()
    val error: LiveData<Event<Int>> get() = _errorId

    private val _navigationEvents = MutableLiveData<Event<NavigationCommand>>()
    val navigationEvent: LiveData<Event<NavigationCommand>> get() = _navigationEvents

    private val _showLoadingBar = MutableLiveData<Event<Boolean>>()
    val showLoadingBar: LiveData<Event<Boolean>> get() = _showLoadingBar

    private val disposables = CompositeDisposable()

    init {
        _currentStage.postValue(Stage.INSPECTION)
        _currentStep.postValue(1)
        _totalSteps.postValue(3)
        _shopTitle.postValue("Test Shop")
        _shopAddress.postValue("Test Address")
        _taskText.postValue("some task...")
        _rating.postValue(Rating.BAD)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun initInspection(stage: Stage) {
        //todo
    }

    fun selectRating(rating: Rating) {
        _rating.postValue(rating)
    }

    fun onNextStep(view: View) {
        //todo
        _currentStage.postValue(Stage.FEEDBACK)
    }

    fun onPreviousStep(view: View) {
        //todo
    }

    fun onLeaveInspection(view: View) {
        //todo
    }
}

enum class Stage {
    INSPECTION,
    FEEDBACK
}

enum class Rating(rating: Int) {
    WORST(1),
    BAD(2),
    GOOD(3),
    BEST(4)
}