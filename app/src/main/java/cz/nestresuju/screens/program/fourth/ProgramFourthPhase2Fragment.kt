package cz.nestresuju.screens.program.fourth

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Fragment with first part of fourth program.
 */
class ProgramFourthPhase2Fragment : ProgramFourthBaseTextQuestionFragment() {

    override val questionRes = R.string.program_4_positives_question

    override val viewModel by viewModel<ProgramFourthTextQuestionViewModel> { parametersOf(2) }
    override val phaseProgress = 2

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_4_2_to_fragment_program_4_question_intro)
    }
}