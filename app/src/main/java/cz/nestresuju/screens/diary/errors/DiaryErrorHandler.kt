package cz.nestresuju.screens.diary.errors

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import cz.nestresuju.R
import cz.nestresuju.model.errors.handlers.FragmentErrorHandler

/**
 * Error handler for handling specific errors that can occur in diary screen.
 */
class DiaryErrorHandler : FragmentErrorHandler {

    override fun handleError(handlingView: Fragment, error: Throwable): Boolean {
        with(handlingView) {
            view?.let {
                return when (error) {
                    is EmptyAnswerException -> {
                        Snackbar.make(it, R.string.diary_empty_answer_error, Snackbar.LENGTH_LONG).show()
                        true
                    }
                    else -> false
                }
            }
        }
        return false
    }
}