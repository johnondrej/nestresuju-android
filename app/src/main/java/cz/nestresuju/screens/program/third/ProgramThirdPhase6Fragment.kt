package cz.nestresuju.screens.program.third

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Fragment with sixth part of third program.
 */
class ProgramThirdPhase6Fragment : ProgramThirdBaseGoalQuestionFragment() {

    override val questionRes = R.string.program_3_goal_question_2

    override val viewModel by viewModel<ProgramThirdGoalQuestionViewModel> { parametersOf(6) }
    override val phaseProgress = 6

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_3_6_to_fragment_program_3_7)
    }
}