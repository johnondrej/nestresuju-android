package cz.nestresuju.router

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.ackee.ackroutine.OAuthManager

/**
 * [ViewModel] responsible for routing user to correct part of the app after launch.
 */
class RouterViewModel(
    oAuthManager: OAuthManager
) : ViewModel() {

    private val _routeLiveData = MutableLiveData<InitialRoute>()
    val routeStream: LiveData<InitialRoute> = _routeLiveData

    init {
        _routeLiveData.value = when {
            oAuthManager.accessToken.isNullOrBlank() -> InitialRoute.Login // TODO: add refresh token check when it's being sent by the API
            else -> InitialRoute.Main
        }
    }
}