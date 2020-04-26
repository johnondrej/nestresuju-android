package cz.nestresuju.screens.tests.input

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.screens.tests.BaseTestIntroFragment

/**
 * Fragment for screen with info about input test.
 */
class InputTestIntroFragment : BaseTestIntroFragment() {

    override val headlineTextRes = R.string.input_test_intro_headline
    override val intro1TextRes = R.string.input_test_intro_1
    override val intro2TextRes = R.string.input_test_intro_2

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_intro_to_fragment_input_test)
    }
}