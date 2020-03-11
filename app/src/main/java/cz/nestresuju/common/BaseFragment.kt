package cz.nestresuju.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import cz.nestresuju.R
import cz.nestresuju.model.errors.InternetConnectionException
import cz.nestresuju.model.errors.ServerException
import cz.nestresuju.model.errors.UnknownException

/**
 * Base [Fragment] with support for error handling and another common cases.
 */
abstract class BaseFragment<B : ViewBinding> : Fragment() {

    protected abstract val viewModel: BaseViewModel

    protected var _binding: B? = null
    protected val binding: B
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

    protected open fun handleError(error: Throwable) {
        view?.let {
            when (error) {
                is InternetConnectionException -> Snackbar.make(it, R.string.error_internet_connection, Snackbar.LENGTH_LONG).show()
                is ServerException ->
                    Snackbar.make(it, getString(R.string.error_general_format, error.errorCode, error.description), Snackbar.LENGTH_LONG).show()
                is UnknownException -> Snackbar.make(it, R.string.error_unknown, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}