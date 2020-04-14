package cz.nestresuju.screens.program.third

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Fragment with ninth part of third program.
 */
class ProgramThirdPhase9Fragment : ProgramThirdBaseGoalQuestionFragment() {

    override val questionRes = R.string.program_3_goal_question_5

    override val viewModel by viewModel<ProgramThirdGoalQuestionViewModel> { parametersOf(9) }
    override val phaseProgress = 9

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_3_9_to_fragment_program_3_10)
    }
}