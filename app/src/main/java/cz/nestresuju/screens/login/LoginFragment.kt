package cz.nestresuju.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import cz.nestresuju.R
import cz.nestresuju.common.BaseFragment
import cz.nestresuju.databinding.FragmentLoginBinding
import cz.nestresuju.model.errors.InvalidCredentialsException
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for login screen.
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentLoginBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginStream.observe(viewLifecycleOwner, Observer {
            // TODO: redirect to app
            Snackbar.make(view, "Přihlášení úspěšné!", Snackbar.LENGTH_LONG).show()
        })

        binding.btnLogin.setOnClickListener {
            viewModel.login(username = binding.editEmail.text.toString(), password = binding.editPassword.text.toString())
        }
    }

    override fun handleError(error: Throwable) {
        view?.let {
            when (error) {
                is InvalidCredentialsException -> Snackbar.make(it, R.string.login_error_invalid_credentials, Snackbar.LENGTH_LONG).show()
                else -> super.handleError(error)
            }
        }
    }
}