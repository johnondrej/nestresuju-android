package cz.nestresuju.model.errors.handlers

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import cz.nestresuju.R
import cz.nestresuju.model.errors.UnknownException

/**
 * Handle unknown errors by showing snackbar (and logging it to crash reporting service).
 */
class UnknownErrorsHandler : FragmentErrorHandler {

    override fun handleError(handlingView: Fragment, error: Throwable): Boolean {
        with(handlingView) {
            view?.let {
                return when (error) {
                    is UnknownException -> {
                        // TODO: log to Crashlytics
                        Snackbar.make(it, R.string.error_unknown, Snackbar.LENGTH_LONG).show()
                        true
                    }
                    else -> false
                }
            }
        }
        return false
    }
}