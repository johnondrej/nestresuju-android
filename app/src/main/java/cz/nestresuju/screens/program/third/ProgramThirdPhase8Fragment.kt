package cz.nestresuju.screens.program.third

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Fragment with eighth part of third program.
 */
class ProgramThirdPhase8Fragment : ProgramThirdBaseGoalQuestionFragment() {

    override val questionRes = R.string.program_3_goal_question_4

    override val viewModel by viewModel<ProgramThirdGoalQuestionViewModel> { parametersOf(8) }
    override val phaseProgress = 8

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_3_8_to_fragment_program_3_9)
    }
}