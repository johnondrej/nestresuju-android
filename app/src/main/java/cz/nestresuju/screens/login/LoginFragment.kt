package cz.nestresuju.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import cz.nestresuju.MainActivity
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.databinding.FragmentLoginBinding
import cz.nestresuju.model.common.State
import cz.nestresuju.model.errors.handlers.InternetErrorsHandler
import cz.nestresuju.model.errors.handlers.UnknownErrorsHandler
import cz.nestresuju.screens.base.BaseFragment
import cz.nestresuju.screens.tests.input.InputTestsActivity
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

                    requireActivity().let { fragmentActivity ->
                        if (!checklist.inputTestSubmitted) {
                            InputTestsActivity.launch(context = fragmentActivity, redirectToScreeningTest = false)
                        } else if (!checklist.screeningTestSubmitted) {
                            InputTestsActivity.launch(context = fragmentActivity, redirectToScreeningTest = true)
                        } else {
                            MainActivity.launch(context = fragmentActivity)
                        }
                        fragmentActivity.finish()
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

        viewBinding.editPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewBinding.btnLogin.performClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    override fun onConsentConfirmed(confirmed: Boolean) {
        viewModel.onConsentConfirmed(confirmed)
    }

    private fun showConsentDialog() {
        LoginConsentDialogFragment().show(childFragmentManager, TAG_CONSENT_DIALOG)
    }
}