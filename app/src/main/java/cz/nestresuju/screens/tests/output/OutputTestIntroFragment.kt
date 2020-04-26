package cz.nestresuju.screens.tests.output

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.screens.tests.BaseTestIntroFragment

/**
 * Fragment for screen with info about output test.
 */
class OutputTestIntroFragment : BaseTestIntroFragment() {

    override val headlineTextRes = R.string.output_test_title
    override val intro1TextRes = R.string.output_test_intro_1
    override val intro2TextRes = R.string.output_test_intro_2

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_output_test_intro_to_fragment_output_test)
    }
}