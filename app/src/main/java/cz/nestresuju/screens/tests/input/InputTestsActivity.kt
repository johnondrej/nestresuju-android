package cz.nestresuju.screens.tests.input

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import cz.nestresuju.R
import cz.nestresuju.common.interfaces.OnBackPressedListener

/**
 * Activity containing both input test and screening test.
 */
class InputTestsActivity : AppCompatActivity() {

    companion object {

        private const val KEY_START_FROM_SCREENING = "redirect_to_screening_test"

        fun launch(context: Context, redirectToScreeningTest: Boolean = false) {
            val intent = Intent(context, InputTestsActivity::class.java).apply {
                putExtra(KEY_START_FROM_SCREENING, redirectToScreeningTest)
            }
            context.startActivity(intent)
        }
    }

    private val redirectToScreening: Boolean by lazy(LazyThreadSafetyMode.NONE) { intent.getBooleanExtra(KEY_START_FROM_SCREENING, false) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_input_test_intro,
                R.id.fragment_input_test,
                R.id.fragment_screening_test
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        if (savedInstanceState == null) {
            if (redirectToScreening) {
                navController.navigate(R.id.fragment_screening_test)
            }
        }
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navHostFragment?.let { navFragment ->
            navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                val handledByFragment = (fragment as? OnBackPressedListener)?.onBackPressed() ?: false
                if (handledByFragment) {
                    return
                }
            }
        }
        super.onBackPressed()
    }
}