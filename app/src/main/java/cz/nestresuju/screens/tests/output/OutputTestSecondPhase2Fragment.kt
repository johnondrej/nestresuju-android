package cz.nestresuju.screens.tests.output

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.model.entities.domain.tests.input.InputTestAnswer

/**
 * Fragment for second phase of second part of output test.
 */
class OutputTestSecondPhase2Fragment : BaseOutputTestSecondQuestionnaireFragment() {

    override val testProgress = 2
    override val questionRes = R.string.output_test_2_question_2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            viewModel.screenStateStream.observe(viewLifecycleOwner, Observer { screenState ->
                if (screenState.stressManagementAfter != null) {
                    questionView.selectAnswer(screenState.stressManagementAfter)
                } else {
                    questionView.unselectAll()
                }
            })

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onAnswerSelected(answer: InputTestAnswer) {
        viewModel.stressManagementAfterChanged(answer)
    }

    override fun onNextQuestionSelected() {
        findNavController().navigate(R.id.action_fragment_output_test_2_phase_2_to_fragment_output_test_2_phase_3)
    }
}