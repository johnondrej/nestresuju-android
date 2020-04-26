package cz.nestresuju.screens.tests.output

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.screens.tests.BaseTestFragment
import cz.nestresuju.screens.tests.TestConfirmationDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for showing output tests.
 */
class OutputTestFirstFragment : BaseTestFragment() {

    companion object {

        private const val TAG_CONFIRMATION_DIALOG = "confirmation_dialog"
    }

    override val viewModel by viewModel<OutputTestFirstViewModel>()

    override fun onTestSubmitted() {
        TestConfirmationDialogFragment.newInstance(textRes = R.string.output_test_confirmation_dialog_text)
            .show(childFragmentManager, TAG_CONFIRMATION_DIALOG)
    }

    override fun onTestConfirmed() {
        findNavController().navigate(R.id.action_fragment_output_test_1_to_fragment_output_test_2)
    }
}