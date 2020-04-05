package cz.nestresuju.screens.program.first

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment with first part of first program.
 */
class ProgramFirstPhase1Fragment : ProgramFirstBaseQuestionFragment() {

    override val questionRes = R.string.program_1_question_1

    override val viewModel by viewModel<ProgramFirstQuestionViewModel>()
    override val phaseProgress = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnBack.visible = false
    }

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_1_1_to_fragment_program_1_2)
    }
}