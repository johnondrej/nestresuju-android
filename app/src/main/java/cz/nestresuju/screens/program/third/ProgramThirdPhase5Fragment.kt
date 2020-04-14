package cz.nestresuju.screens.program.third

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Fragment with fifth part of third program.
 */
class ProgramThirdPhase5Fragment : ProgramThirdBaseGoalQuestionFragment() {

    override val questionRes = R.string.program_3_goal_question_1

    override val viewModel by viewModel<ProgramThirdGoalQuestionViewModel> { parametersOf(5) }
    override val phaseProgress = 5

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnBack.visible = false
    }

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_3_5_to_fragment_program_3_6)
    }
}