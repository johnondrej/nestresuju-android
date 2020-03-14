package cz.nestresuju.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.nestresuju.common.BaseViewModel
import cz.nestresuju.model.common.EmptyStateLiveData
import cz.nestresuju.model.repositories.AuthRepository

/**
 * [ViewModel] for login screen.
 */
class LoginViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    val loginStream = EmptyStateLiveData()

    fun login(username: String, password: String) {
        viewModelScope.launchWithErrorHandling {
            loginStream.loading()
            authRepository.login(username, password)
            loginStream.loaded()
        }
    }
}