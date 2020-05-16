package com.example.secretcustomer.domain.customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secretcustomer.data.*
import com.example.secretcustomer.ui.fragments.customer.ShopInspectionFragmentDirections
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
class InspectionViewModel
@Inject constructor(
    val secretCustomerApiService: SecretCustomerApiService,
    val feedbackApiService: FeedbackApiService,
    @Named("secure") val secureSharedPrefs: SharedPreferencesWrapper
) : ViewModel() {
    private lateinit var shop: Shop
    private var session: Session? = null
    private lateinit var actions: List<Action>
    private var responses: Array<String>

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
        _totalSteps.postValue(1)
        _taskReport.postValue("")
        responses = Array(2) { "" }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun initInspection(stage: Stage, shop: Shop) {
        _currentStage.postValue(stage)
        _shopTitle.postValue(shop.name)
        _shopAddress.postValue(shop.address)
        this.shop = shop
        if (stage == Stage.INSPECTION) {
            _showLoadingBar.postValue(Event(true))
            secureSharedPrefs.getString(LoginConstants.TOKEN)?.let { token ->
                disposables.add(
                    secretCustomerApiService.startSession(token, SessionStartData(shop.id))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                            { throw it },
                            { response ->
                                session = response
                                loadActions(token, shop)
                            }
                        )
                )
            }
        }
    }

    fun selectRating(rating: Rating) {
        _rating.postValue(rating)
    }

    fun onNextStep(view: View) {
        if (currentStage.value == Stage.INSPECTION) {
            if (currentStep.value == totalSteps.value) {
                rememberState()
                _currentStage.value = Stage.FEEDBACK
                restoreState()
            } else {
                rememberState()
                _currentStep.value = currentStep.value!! + 1
                restoreState()
            }
        } else {
            rememberState()
            saveResults()
        }
    }

    fun onPreviousStep(view: View) {
        when {
            currentStep.value == 1 -> {
                _navigationEvents.postValue(Event(NavigationCommand.Finish))
            }
            currentStage.value == Stage.FEEDBACK -> {
                rememberState()
                _currentStage.value = Stage.INSPECTION
                restoreState()
            }
            else -> {
                rememberState()
                _currentStep.value = currentStep.value!! - 1
                restoreState()
            }
        }
    }

    fun onLeaveInspection(view: View) {
        _navigationEvents.postValue(Event(NavigationCommand.Finish))
    }

    private fun loadActions(token: String, shop: Shop) {
        disposables.add(
            secretCustomerApiService.getActions(token, shop.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    {},
                    { response ->
                        actions = response
                        _totalSteps.postValue(actions.size)
                        _taskText.postValue(actions[0].action)
                        _showLoadingBar.postValue(Event(false))
                        responses = Array(actions.size + 1) { "" }
                    }
                )
        )
    }

    private fun rememberState() {
        val currentIndex = -1 +
            if (currentStage.value == Stage.FEEDBACK) currentStep.value!! + 1 else currentStep.value!!
        responses[currentIndex] = taskReport.value!!
    }

    private fun restoreState() {
        val currentIndex = -1 +
            if (currentStage.value == Stage.FEEDBACK) currentStep.value!! + 1 else currentStep.value!!
        _taskReport.postValue(responses[currentIndex])
        if (currentStage.value == Stage.INSPECTION) {
            _taskText.postValue(actions[currentIndex].action)
        }
    }

    private fun saveResults() {

        _showLoadingBar.postValue(Event(true))
        if (session != null) {
            secureSharedPrefs.getString(LoginConstants.TOKEN)?.let { token ->
                disposables.add(
                    secretCustomerApiService.nextSessionStage(token, session!!.id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap {
                            secretCustomerApiService.endSession(
                                token,
                                session!!.id,
                                SessionPostData(
                                    shopId = shop.id,
                                    pros = shop.address,
                                    cons = "",
                                    //todo разобраться как нормально получить значение енама
                                    rating = rating.value!!.ordinal + 1,
                                    additionalInfo = responses.last()
                                )
                            )
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                        }
                        .subscribeBy(
                            {},
                            { _ ->
                                _showLoadingBar.postValue(Event(false))
                                _navigationEvents.postValue(
                                    Event(
                                        NavigationCommand.To(
                                            ShopInspectionFragmentDirections.actionShopInspectionFragmentToShopInspectionFinishFragment()
                                        )
                                    )
                                )
                            }
                        )
                )
            }
        } else {
            secureSharedPrefs.getString(LoginConstants.TOKEN)?.let { token ->
                disposables.add(
                    feedbackApiService.leaveFeedback(
                        token,
                        FeedbackPostData(
                            shopId = shop.id,
                            pros = shop.address,
                            cons = "",
                            //todo разобраться как нормально получить значение енама
                            rating = rating.value!!.ordinal + 1,
                            additionalInfo = responses.last()
                        )
                    )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                            {},
                            { _ ->
                                _showLoadingBar.postValue(Event(false))
                                _navigationEvents.postValue(
                                    Event(
                                        NavigationCommand.To(
                                            ShopInspectionFragmentDirections.actionShopInspectionFragmentToShopInspectionFinishFragment()
                                        )
                                    )
                                )
                            }
                        )
                )
            }
        }
    }
}

enum class Stage(name: String) {
    INSPECTION("INSPECTION"),
    FEEDBACK("FEEDBACK")
}

enum class Rating(value: Int) {
    WORST(1),
    BAD(2),
    GOOD(3),
    BEST(4)
}