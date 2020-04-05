package cz.nestresuju.screens.program.first

import android.os.Bundle
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment with first part of first program.
 */
class ProgramFirstPhase2Fragment : ProgramFirstBaseQuestionFragment() {

    override val questionRes = R.string.program_1_question_2
    override val phaseProgress = 2

    override val viewModel by viewModel<ProgramFirstQuestionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
            isEnabled = true
        }
    }

    override fun onContinueClicked() {
        // TODO
    }
}