package cz.nestresuju.screens.program.first

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Fragment with fifth part of first program.
 */
class ProgramFirstPhase5Fragment : ProgramFirstBaseQuestionFragment() {

    override val questionRes = R.string.program_1_question_5
    override val phaseProgress = 5

    override val viewModel by viewModel<ProgramFirstQuestionViewModel> { parametersOf(5) }

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_1_5_to_fragment_program_1_6)
    }
}