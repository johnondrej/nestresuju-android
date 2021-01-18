package cz.nestresuju.screens.tests.output

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.hideKeyboard
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.databinding.FragmentOutputTest2Question3Binding
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Fragment for third phase of second part of output test.
 */
class OutputTestSecondPhase3Fragment : BaseArchFragment<FragmentOutputTest2Question3Binding>() {

    override val viewModel by sharedViewModel<OutputTestSecondViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentOutputTest2Question3Binding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            progress.max = OutputTestConstants.PHASES
            progress.progress = 3

            editAnswer.setText(viewModel.screenStateStream.value!!.helpComment)
            viewModel.screenStateStream.value!!.wasAppHelpful?.let { appHelpful ->
                radioGroupAnswers.check(
                    when (appHelpful) {
                        true -> R.id.radio_btn_answer_yes
                        false -> R.id.radio_btn_answer_no
                    }
                )
            }

            radioGroupAnswers.setOnCheckedChangeListener { _, checkedId ->
                val appHelpful = checkedId == R.id.radio_btn_answer_yes
                viewModel.appHelpfulAnswerChanged(answer = appHelpful)
            }

            with(editAnswer) {
                setHorizontallyScrolling(false)
                setLines(10)
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        context?.hideKeyboard(view)
                    }
                    return@setOnEditorActionListener true
                }
                doAfterTextChanged { answer ->
                    viewModel.helpCommentChanged(answer?.toString() ?: "")
                }
            }

            btnContinue.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_output_test_2_phase_3_to_fragment_output_test_2_phase_4)
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            viewModel.screenStateStream.observe(viewLifecycleOwner, Observer { screenState ->
                layoutAnswer.visible = screenState.wasAppHelpful ?: false
                btnContinue.isEnabled = screenState.wasAppHelpful != null
            })
        }
    }
}