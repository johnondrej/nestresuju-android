package cz.nestresuju.screens.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import cz.nestresuju.model.errors.handlers.SilentInternetErrorsHandler
import cz.nestresuju.model.errors.handlers.UnknownErrorsHandler

/**
 * Base [Fragment] with support for error handling and another common cases.
 */
abstract class BaseArchFragment<B : ViewBinding> : Fragment() {

    protected abstract val viewModel: BaseViewModel

    protected open val errorHandlers = arrayOf(
        SilentInternetErrorsHandler(),
        UnknownErrorsHandler()
    )

    protected var _binding: B? = null
    protected val viewBinding: B
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorStream.observe(viewLifecycleOwner, Observer { error ->
            handleError(error)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleError(error: Throwable) {
        for (errorHandler in errorHandlers) {
            val handled = errorHandler.handleError(handlingView = this, error = error)
            if (handled) {
                return
            }
        }

        throw error
    }
}