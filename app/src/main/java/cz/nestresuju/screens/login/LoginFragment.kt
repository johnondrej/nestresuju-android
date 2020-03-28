package cz.nestresuju.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.nestresuju.MainActivity
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.databinding.FragmentLoginBinding
import cz.nestresuju.model.common.State
import cz.nestresuju.model.errors.handlers.InternetErrorsHandler
import cz.nestresuju.model.errors.handlers.UnknownErrorsHandler
import cz.nestresuju.screens.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for login screen.
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>(), LoginConsentDialogFragment.OnConfirmedListener {

    companion object {

        private const val TAG_CONSENT_DIALOG = "consent_dialog"
    }

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
                    val checklist = state.data

                    if (!checklist.inputTestSubmitted) {
                        // TODO: route to input test
                    } else if (!checklist.screeningTestSubmitted) {
                        // TODO: route to screening test
                    } else {
                        // TODO: route to app here when routes above are implemented
                    }

                    requireActivity().let {
                        MainActivity.launch(context = it)
                        it.finish()
                    }
                }
            }

            val enableInput = state != State.Loading

            viewBinding.progress.visible = state == State.Loading
            viewBinding.btnLogin.isEnabled = enableInput
            viewBinding.editEmail.isEnabled = enableInput
            viewBinding.editPassword.isEnabled = enableInput
        })

        viewModel.consentStream.observe(viewLifecycleOwner, Observer { showConsentDialog() })

        viewBinding.btnLogin.setOnClickListener {
            viewModel.login(username = viewBinding.editEmail.text.toString(), password = viewBinding.editPassword.text.toString())
        }
    }

    override fun onConsentConfirmed(confirmed: Boolean) {
        viewModel.onConsentConfirmed(confirmed)
    }

    private fun showConsentDialog() {
        LoginConsentDialogFragment().show(childFragmentManager, TAG_CONSENT_DIALOG)
    }
}