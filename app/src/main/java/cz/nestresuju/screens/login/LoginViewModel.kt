package cz.nestresuju.screens.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.nestresuju.common.BaseViewModel
import cz.nestresuju.model.repositories.AuthRepository

/**
 * [ViewModel] for login screen.
 */
class LoginViewModel(private val authRepository: AuthRepository) : BaseViewModel() {

    private val _loginLiveData = MutableLiveData<Unit>()
    val loginStream = _loginLiveData

    fun login(username: String, password: String) {
        viewModelScope.launchWithErrorHandling {
            authRepository.login(username, password)
            _loginLiveData.value = Unit
        }
    }
}