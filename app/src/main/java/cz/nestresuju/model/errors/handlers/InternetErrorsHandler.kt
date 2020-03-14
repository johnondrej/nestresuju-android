package cz.nestresuju.model.errors.handlers

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import cz.nestresuju.R
import cz.nestresuju.model.errors.InternetConnectionException
import cz.nestresuju.model.errors.ServerException

/**
 * Handle internet connection related exceptions by showing snackbar.
 */
class InternetErrorsHandler : FragmentErrorHandler {

    override fun handleError(handlingView: Fragment, error: Throwable): Boolean {
        with(handlingView) {
            view?.let {
                return when (error) {
                    is InternetConnectionException -> {
                        Snackbar.make(it, R.string.error_internet_connection, Snackbar.LENGTH_LONG).show()
                        return true
                    }
                    is ServerException -> {
                        Snackbar.make(it, getString(R.string.error_general_format, error.errorCode, error.description), Snackbar.LENGTH_LONG).show()
                        return true
                    }
                    else -> false
                }
            }
        }
        return false
    }
}