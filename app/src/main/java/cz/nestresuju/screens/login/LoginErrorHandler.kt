package cz.nestresuju.screens.login

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import cz.nestresuju.R
import cz.nestresuju.model.errors.ConsentNotGivenException
import cz.nestresuju.model.errors.InvalidCredentialsException
import cz.nestresuju.model.errors.handlers.FragmentErrorHandler

/**
 * Error handler for handling errors related to logging in.
 */
class LoginErrorHandler : FragmentErrorHandler {

    override fun handleError(handlingView: Fragment, error: Throwable): Boolean {
        with(handlingView) {
            view?.let {
                return when (error) {
                    is InvalidCredentialsException -> {
                        Snackbar.make(it, R.string.login_error_invalid_credentials, Snackbar.LENGTH_LONG).show()
                        true
                    }
                    is ConsentNotGivenException -> {
                        Snackbar.make(it, R.string.login_consent_missing_error, Snackbar.LENGTH_LONG).show()
                        true
                    }
                    else -> false
                }
            }
        }
        return false
    }
}