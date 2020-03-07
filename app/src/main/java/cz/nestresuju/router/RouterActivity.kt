package cz.nestresuju.router

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.nestresuju.screens.login.LoginActivity

/**
 * Entry activity that routes user to another parts of the app.
 */
class RouterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        route()
        finish()
    }

    private fun route() {
        // TODO: route to application when user is already logged in
        LoginActivity.launch(context = this)
    }
}