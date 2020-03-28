package cz.nestresuju.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import cz.nestresuju.model.common.StateLiveData
import cz.nestresuju.model.entities.domain.auth.LoginChecklistCompletion
import cz.nestresuju.model.repositories.AuthRepository
import cz.nestresuju.screens.base.BaseViewModel

/**
 * [ViewModel] for login screen.
 */
class LoginViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    val loginStream = StateLiveData<LoginChecklistCompletion>()
    val consentStream = LiveEvent<Unit>()

    fun login(username: String, password: String) {
        viewModelScope.launchWithErrorHandling(errorPropagationStreams = arrayOf(loginStream)) {
            loginStream.loading()
            val loginChecklist = authRepository.login(username, password, onShowConsent = { consentStream.value = Unit })
            loginStream.loaded(loginChecklist)
        }
    }

    fun onConsentConfirmed(confirmed: Boolean) {
        authRepository.onConsentConfirmed(confirmed)
    }
}