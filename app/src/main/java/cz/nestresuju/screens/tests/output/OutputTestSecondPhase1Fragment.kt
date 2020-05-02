package cz.nestresuju.screens.tests.output

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.model.entities.domain.tests.input.InputTestAnswer

/**
 * Fragment for first phase of second part of output test.
 */
class OutputTestSecondPhase1Fragment : BaseOutputTestSecondQuestionnaireFragment() {

    override val testProgress = 1
    override val questionRes = R.string.output_test_2_question_1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            btnBack.visible = false

            viewModel.screenStateStream.observe(viewLifecycleOwner, Observer { screenState ->
                if (screenState.stressManagementBefore != null) {
                    questionView.selectAnswer(screenState.stressManagementBefore)
                } else {
                    questionView.unselectAll()
                }
            })
        }
    }

    override fun onAnswerSelected(answer: InputTestAnswer) {
        viewModel.stressManagementBeforeChanged(answer)
    }

    override fun onNextQuestionSelected() {
        findNavController().navigate(R.id.action_fragment_output_test_2_phase_1_to_fragment_output_test_2_phase_2)
    }
}