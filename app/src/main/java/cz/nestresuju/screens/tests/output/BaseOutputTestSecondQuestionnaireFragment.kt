package cz.nestresuju.screens.tests.output

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.databinding.FragmentInputTestBinding
import cz.nestresuju.model.entities.domain.tests.input.InputTestAnswer
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Base fragment for questionnare part of output test 2.
 */
abstract class BaseOutputTestSecondQuestionnaireFragment : BaseArchFragment<FragmentInputTestBinding>() {

    override val viewModel: OutputTestSecondViewModel by sharedViewModel()

    abstract val testProgress: Int
    abstract val questionRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentInputTestBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            layoutContent.visible = true
            txtInstructions.visible = false
            questionsProgress.max = OutputTestConstants.PHASES
            questionsProgress.progress = testProgress

            questionView.question = requireContext().getString(questionRes)
            questionView.setAnswersOptions(
                arrayOf(
                    getString(R.string.output_test_2_stress_management_answer_1),
                    getString(R.string.output_test_2_stress_management_answer_2),
                    getString(R.string.output_test_2_stress_management_answer_3),
                    getString(R.string.output_test_2_stress_management_answer_4),
                    getString(R.string.output_test_2_stress_management_answer_5)
                )
            )
            questionView.setOnAnswerSelectedListener { answer ->
                onAnswerSelected(answer)
            }

            btnContinue.setOnClickListener {
                onNextQuestionSelected()
            }
        }
    }

    abstract fun onAnswerSelected(answer: InputTestAnswer)

    abstract fun onNextQuestionSelected()
}