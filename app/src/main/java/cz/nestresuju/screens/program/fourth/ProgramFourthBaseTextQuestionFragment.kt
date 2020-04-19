package cz.nestresuju.screens.program.fourth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import cz.nestresuju.common.extensions.hideKeyboard
import cz.nestresuju.databinding.FragmentProgram1QuestionBinding
import cz.nestresuju.screens.base.BaseArchFragment

/**
 * Base fragment with text question in fourth program's phase.
 */
abstract class ProgramFourthBaseTextQuestionFragment : BaseArchFragment<FragmentProgram1QuestionBinding>() {

    protected abstract val questionRes: Int
    protected abstract val phaseProgress: Int

    abstract override val viewModel: ProgramFourthTextQuestionViewModel

    protected abstract fun onContinueClicked()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram1QuestionBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            progress.progress = phaseProgress
            progress.max = ProgramFourthConstants.PHASES
            txtQuestion.text = getString(questionRes)
            btnContinue.setOnClickListener { onContinueClicked() }
            btnBack.setOnClickListener { activity?.onBackPressed() }

            with(editAnswer) {
                setHorizontallyScrolling(false)
                setLines(10)
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        if (!editAnswer.text.isNullOrBlank()) {
                            context?.hideKeyboard(view)
                            onContinueClicked()
                        }
                        return@setOnEditorActionListener true
                    }
                    return@setOnEditorActionListener false
                }
                doAfterTextChanged { answer ->
                    viewModel.onAnswerChanged(answer.toString())
                }
            }

            viewModel.answerStream.observe(viewLifecycleOwner, Observer { answer ->
                if (answer.isNotBlank() && editAnswer.text.isNullOrBlank()) {
                    editAnswer.setText(answer)
                }

                btnContinue.isEnabled = answer.isNotBlank()
            })
        }
    }
}