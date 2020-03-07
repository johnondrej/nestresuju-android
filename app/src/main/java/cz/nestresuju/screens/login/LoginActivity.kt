package cz.nestresuju.screens.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import cz.nestresuju.R

/**
 * Activity that hosts login screen.
 */
class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}
