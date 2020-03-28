package cz.nestresuju.model.errors.handlers

import androidx.fragment.app.Fragment
import cz.nestresuju.model.errors.InternetConnectionException
import cz.nestresuju.model.errors.ServerException

/**
 * Just catch internet connection errors and ignore them. Useful on screens without mandatory connection (usually with local database support).
 */
class SilentInternetErrorsHandler : FragmentErrorHandler {

    override fun handleError(handlingView: Fragment, error: Throwable): Boolean {
        return when (error) {
            is InternetConnectionException, is ServerException -> true
            else -> false
        }
    }
}