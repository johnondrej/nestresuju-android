package cz.nestresuju.screens.tests.input

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import cz.nestresuju.R

/**
 * Activity containing both input test and screening test.
 */
class InputTestsActivity : AppCompatActivity() {

    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, InputTestsActivity::class.java)
            context.startActivity(intent)
        }
    }

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
    }
}