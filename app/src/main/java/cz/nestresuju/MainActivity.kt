package cz.nestresuju

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import cz.nestresuju.common.interfaces.OnBackPressedListener
import cz.nestresuju.router.DeepLink

class MainActivity : AppCompatActivity() {

    companion object {

        private const val EXTRA_DEEP_LINK = "deep_link"

        fun launch(context: Context, deepLink: String? = null) {
            val intent = Intent(context, MainActivity::class.java).apply {
                if (deepLink != null) {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                deepLink?.let { putExtra(EXTRA_DEEP_LINK, deepLink) }
            }
            context.startActivity(intent)
        }
    }

    private val deepLink by lazy(LazyThreadSafetyMode.NONE) { intent?.extras?.getString(EXTRA_DEEP_LINK)?.let { DeepLink.fromClickAction(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_program,
                R.id.navigation_diary,
                R.id.navigation_library,
                R.id.navigation_about_app
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (savedInstanceState == null) {
            handleDeepLink()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeepLink()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return when (navController.currentDestination?.id) {
            R.id.navigation_library, R.id.fragment_about_app_feedback -> {
                // enable custom "up" button handling for some fragments
                onBackPressed()
                true
            }
            else -> {
                navController.navigateUp() || super.onSupportNavigateUp()
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

    private fun handleDeepLink() {
        val navController = findNavController(R.id.nav_host_fragment)

        when (deepLink) {
            DeepLink.DIARY -> navController.navigate(R.id.navigation_diary)
            DeepLink.PROGRAM, DeepLink.OUTPUT_TEST -> navController.navigate(R.id.navigation_program)
        }
    }
}
