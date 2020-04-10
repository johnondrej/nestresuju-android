package cz.nestresuju.router

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.ackee.ackroutine.OAuthManager
import cz.nestresuju.model.database.sharedprefs.SharedPreferencesInteractor

/**
 * [ViewModel] responsible for routing user to correct part of the app after launch.
 */
class RouterViewModel(
    oAuthManager: OAuthManager,
    sharedPreferencesInteractor: SharedPreferencesInteractor
) : ViewModel() {

    private val _routeLiveData = MutableLiveData<InitialRoute>()
    val routeStream: LiveData<InitialRoute>
        get() = _routeLiveData

    init {
        _routeLiveData.value = when {
            oAuthManager.accessToken.isNullOrBlank() || oAuthManager.refreshToken.isNullOrBlank() || !sharedPreferencesInteractor.isConsentGiven() -> InitialRoute.Login
            !sharedPreferencesInteractor.isInputTestCompleted() -> InitialRoute.InputTest
            !sharedPreferencesInteractor.isScreeningTestCompleted() -> InitialRoute.ScreeningTest
            else -> InitialRoute.Main
        }
    }
}