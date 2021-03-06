package cz.nestresuju.screens.tests.input

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.screens.tests.BaseTestFragment
import cz.nestresuju.screens.tests.TestConfirmationDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for showing input test after login.
 */
class InputTestFragment : BaseTestFragment() {

    companion object {

        private const val TAG_CONFIRMATION_DIALOG = "confirmation_dialog"
    }

    override val viewModel by viewModel<InputTestViewModel>()

    override fun onTestConfirmed() {
        findNavController().navigate(R.id.action_fragment_input_test_to_fragment_screening_test)
    }

    override fun onTestSubmitted() {
        TestConfirmationDialogFragment.newInstance(textRes = R.string.input_test_confirm_dialog_text)
            .show(childFragmentManager, TAG_CONFIRMATION_DIALOG)
    }
}