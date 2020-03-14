package cz.nestresuju.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.common.EmptyStateLiveData
import cz.nestresuju.model.repositories.AuthRepository
import cz.nestresuju.screens.base.BaseViewModel

/**
 * [ViewModel] for login screen.
 */
class LoginViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    val loginStream = EmptyStateLiveData()

    fun login(username: String, password: String) {
        viewModelScope.launchWithErrorHandling(errorPropagationStreams = arrayOf(loginStream)) {
            loginStream.loading()
            authRepository.login(username, password)
            loginStream.loaded()
        }
    }
}