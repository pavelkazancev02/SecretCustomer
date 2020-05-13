package com.example.secretcustomer.domain.customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secretcustomer.data.Shop
import com.example.secretcustomer.data.ShopApiService
import com.example.secretcustomer.util.Event
import com.example.secretcustomer.util.NavigationCommand
import com.example.secretcustomer.util.sharedpreferences.SharedPreferencesWrapper
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

// Инжектим даггером наш сервис, смотреть папку di: RestServiceModule, ViewModelModule
class ShopsViewModel
@Inject constructor(
    val shopApiService: ShopApiService,
    @Named("secure") val secureSharedPrefs: SharedPreferencesWrapper
) : ViewModel() {
    private val _shops = MutableLiveData<Event<List<Shop>>>()
    val shops: LiveData<Event<List<Shop>>> get() = _shops

    // LiveData для управления навигацией, так как она может совершаться только из ui.
    private val _navigationEvents = MutableLiveData<Event<NavigationCommand>>()
    val navigationEvent: LiveData<Event<NavigationCommand>> get() = _navigationEvents

    private val _showLoadingBar = MutableLiveData<Event<Boolean>>()
    val showLoadingBar: LiveData<Event<Boolean>> get() = _showLoadingBar

    private val disposables = CompositeDisposable()

    init {
        _shops.postValue(
            Event(
                listOf(
                    Shop(
                        id = "1",
                        name = "Test Shop",
                        ownerId = 1,
                        address = "address...",
                        balance = 0
                    ),
                    Shop(
                        id = "2",
                        name = "Test Shop2",
                        ownerId = 1,
                        address = "address...",
                        balance = 0
                    )
                )
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun inspectShop(shop: Shop) {
        // todo
    }

    fun leaveFeedback(shop: Shop) {
        // todo
    }

    fun onScanQRClick(view: View) {
        // todo?
    }
}