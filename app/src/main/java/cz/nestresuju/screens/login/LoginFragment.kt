package cz.nestresuju.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import cz.nestresuju.common.BaseFragment
import cz.nestresuju.common.visible
import cz.nestresuju.databinding.FragmentLoginBinding
import cz.nestresuju.model.common.State
import cz.nestresuju.model.errors.handlers.InternetErrorsHandler
import cz.nestresuju.model.errors.handlers.UnknownErrorsHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for login screen.
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val errorHandlers = arrayOf(
        InternetErrorsHandler(),
        UnknownErrorsHandler(),
        LoginErrorHandler()
    )
    override val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentLoginBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginStream.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is State.Loaded -> {
                    Snackbar.make(view, "Přihlášení úspěšné!", Snackbar.LENGTH_LONG).show()
                    // TODO: redirect to app
                }
            }

            val enableInput = state != State.Loading

            viewBinding.progress.visible = state == State.Loading
            viewBinding.btnLogin.isEnabled = enableInput
            viewBinding.editEmail.isEnabled = enableInput
            viewBinding.editPassword.isEnabled = enableInput
        })

        viewBinding.btnLogin.setOnClickListener {
            viewModel.login(username = viewBinding.editEmail.text.toString(), password = viewBinding.editPassword.text.toString())
        }
    }
}