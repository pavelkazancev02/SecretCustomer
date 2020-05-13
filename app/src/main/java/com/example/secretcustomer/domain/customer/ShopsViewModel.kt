package com.example.secretcustomer.domain.customer

import android.app.Application
import android.content.Intent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.secretcustomer.data.Shop
import com.example.secretcustomer.data.ShopApiService
import com.example.secretcustomer.ui.activities.ExtrasKeys
import com.example.secretcustomer.ui.activities.InspectionActivity
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
class ShopsViewModel
@Inject constructor(
    val shopApiService: ShopApiService,
    val application: Application,
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
        _showLoadingBar.postValue(Event(true))
        secureSharedPrefs.getString(LoginConstants.TOKEN)?.let { token ->
            // ToDo pagination
            disposables.add(
                shopApiService.getPaginatedShopList(token, 10, 0)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                        {},
                        { response ->
                            _showLoadingBar.postValue(Event(false))
                            _shops.postValue(Event(response))
                        }
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun inspectShop(shop: Shop) {
        val intent = Intent(application, InspectionActivity::class.java)
        intent.putExtra(ExtrasKeys.STAGE, Stage.INSPECTION)
        intent.putExtra(ExtrasKeys.SHOP, shop)
        _navigationEvents.postValue(Event(NavigationCommand.ToIntent(intent)))
    }

    fun leaveFeedback(shop: Shop) {
        val intent = Intent(application, InspectionActivity::class.java)
        intent.putExtra(ExtrasKeys.STAGE, Stage.FEEDBACK)
        intent.putExtra(ExtrasKeys.SHOP, shop)
        _navigationEvents.postValue(Event(NavigationCommand.ToIntent(intent)))
    }

    fun onScanQRClick(view: View) {
        // todo?
    }
}