package cz.nestresuju.model.logouter

import android.content.Context
import android.content.Intent
import cz.ackee.ackroutine.OAuthManager
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.database.sharedprefs.SharedPreferencesInteractor
import cz.nestresuju.screens.login.LoginActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Class for handling all things during logout.
 */
class LogoutHandler(
    private val applicationContext: Context,
    private val oAuthManager: OAuthManager,
    private val sharedPreferencesInteractor: SharedPreferencesInteractor,
    private val appDatabase: AppDatabase
) {

    fun logout() {
        GlobalScope.launch {
            oAuthManager.clearCredentials()
            sharedPreferencesInteractor.clearAllData()
            appDatabase.clearAllTables()
            AppDatabase.initDefaultValues(applicationContext, appDatabase)
        }
        returnToLoginScreen()
    }

    private fun returnToLoginScreen() {
        val intent = Intent(applicationContext, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        applicationContext.startActivity(intent)
    }
}