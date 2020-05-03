package cz.nestresuju.screens.about

import androidx.lifecycle.ViewModel
import cz.nestresuju.model.logouter.LogoutHandler
import cz.nestresuju.screens.base.BaseViewModel

/**
 * [ViewModel] for "about app" section.
 */
class AboutAppViewModel(
    private val logoutHandler: LogoutHandler
) : BaseViewModel() {

    fun logout() {
        logoutHandler.logout()
    }
}