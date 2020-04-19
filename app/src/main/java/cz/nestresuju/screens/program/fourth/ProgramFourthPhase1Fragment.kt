package cz.nestresuju.screens.program.fourth

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Fragment with first part of fourth program.
 */
class ProgramFourthPhase1Fragment : ProgramFourthBaseTextQuestionFragment() {

    override val questionRes = R.string.program_4_stress_event_question

    override val viewModel by viewModel<ProgramFourthTextQuestionViewModel> { parametersOf(1) }
    override val phaseProgress = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnBack.visible = false
    }

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_4_1_to_fragment_program_4_2)
    }
}