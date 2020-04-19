package cz.nestresuju.screens.program.fourth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.interfaces.OnBackPressedListener
import cz.nestresuju.databinding.FragmentProgram4QuestionBinding
import cz.nestresuju.screens.base.BaseArchFragment
import cz.nestresuju.screens.program.ProgramSubmittedDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment with question from program 4.
 */
class ProgramFourthQuestionFragment :
    BaseArchFragment<FragmentProgram4QuestionBinding>(),
    ProgramSubmittedDialogFragment.OnProgramSubmittedListener,
    OnBackPressedListener {

    companion object {

        private const val TAG_CONFIRMATION_DIALOG = "confirmation_dialog"
    }

    override val viewModel by viewModel<ProgramFourthQuestionViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram4QuestionBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            viewModel.viewStateStream.observe(viewLifecycleOwner, Observer { viewState ->
                progress.max = ProgramFourthConstants.PHASES
                progress.progress = viewState.progress.currentQuestion + 4
                questionView.question = viewState.question.text
                btnContinue.text = if (!viewState.progress.isLast) getString(R.string.general_continue) else getString(R.string.general_finish)
                btnContinue.isEnabled = viewState.answer != null

                if (viewState.answer != null) {
                    questionView.selectAnswer(viewState.answer)
                } else {
                    questionView.unselectAll()
                }

                if (!viewState.progress.isLast) {
                    btnContinue.setOnClickListener {
                        viewModel.nextQuestion()
                    }
                } else {
                    btnContinue.setOnClickListener {
                        onFinishProgramClicked()
                    }
                }
            })

            questionView.setOnAnswerSelectedListener { answer ->
                viewModel.selectAnswer(answer)
            }

            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun onBackPressed(): Boolean {
        if (!viewModel.firstQuestionSelected()) {
            viewModel.previousQuestion()
            return true
        }
        return false
    }

    override fun onProgramSubmitted() {
        findNavController().navigate(R.id.action_fragment_program_4_question_to_fragment_program_4_summary)
    }

    private fun onFinishProgramClicked() {
        viewModel.submitResults()

        ProgramSubmittedDialogFragment.newInstance(textRes = R.string.program_4_submitted_dialog_text)
            .show(childFragmentManager, TAG_CONFIRMATION_DIALOG)
    }
}