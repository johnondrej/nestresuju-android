package cz.nestresuju.screens.program.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import cz.nestresuju.databinding.FragmentProgram1QuestionBinding
import cz.nestresuju.screens.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Base fragment with text question in first program's phase.
 */
abstract class ProgramFirstBaseQuestionFragment : BaseFragment<FragmentProgram1QuestionBinding>() {

    protected abstract val questionRes: Int
    protected abstract val phaseProgress: Int

    override val viewModel by viewModel<ProgramFirstQuestionViewModel>()

    protected abstract fun onContinueClicked()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram1QuestionBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            progress.max = ProgramFirstConstants.PHASES
            progress.progress = phaseProgress
            txtQuestion.text = getString(questionRes)
            btnContinue.setOnClickListener { onContinueClicked() }
            btnBack.setOnClickListener { activity?.onBackPressed() }

            editAnswer.doAfterTextChanged { answer ->
                viewModel.onAnswerChanged(answer.toString())
            }

            viewModel.answerStream.observe(viewLifecycleOwner, Observer { answer ->
                btnContinue.isEnabled = answer.isNotBlank()
            })
        }
    }
}