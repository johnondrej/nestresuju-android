package cz.nestresuju.router

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import cz.nestresuju.MainActivity
import cz.nestresuju.screens.login.LoginActivity
import cz.nestresuju.screens.tests.input.InputTestsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Entry activity that routes user to another parts of the app.
 */
class RouterActivity : AppCompatActivity() {

    private val viewModel by viewModel<RouterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.routeStream.observe(this, Observer { route ->
            when (route) {
                InitialRoute.Login -> LoginActivity.launch(context = this)
                InitialRoute.InputTest -> InputTestsActivity.launch(context = this, redirectToScreeningTest = false)
                InitialRoute.ScreeningTest -> InputTestsActivity.launch(context = this, redirectToScreeningTest = true)
                InitialRoute.Main -> MainActivity.launch(context = this)
            }
            finish()
        })
    }
}