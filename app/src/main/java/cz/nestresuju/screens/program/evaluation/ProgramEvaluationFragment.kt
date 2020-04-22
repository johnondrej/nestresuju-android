package cz.nestresuju.screens.program.evaluation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.hideKeyboard
import cz.nestresuju.databinding.FragmentProgramEvaluationBinding
import cz.nestresuju.model.entities.domain.program.ProgramId
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Fragment for screen with program evaluation.
 */
class ProgramEvaluationFragment : BaseArchFragment<FragmentProgramEvaluationBinding>() {

    companion object {

        private const val KEY_PROGRAM_ID = "program_id"

        fun arguments(programId: ProgramId) = bundleOf(
            KEY_PROGRAM_ID to programId
        )
    }

    private val programId: ProgramId by lazy(LazyThreadSafetyMode.NONE) { requireArguments().getParcelable<ProgramId>(KEY_PROGRAM_ID)!! }

    override val viewModel by viewModel<ProgramEvaluationViewModel> { parametersOf(programId) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgramEvaluationBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            val programName = when (programId) {
                ProgramId.PROGRAM_FIRST_ID -> getString(R.string.program_1_title)
                ProgramId.PROGRAM_SECOND_ID -> getString(R.string.program_2_title)
                ProgramId.PROGRAM_THIRD_ID -> getString(R.string.program_3_title)
                ProgramId.PROGRAM_FOURTH_ID -> getString(R.string.program_4_title)
            }

            txtInstructions.text = getString(R.string.program_evaluation_instructions, programName)

            viewModel.inputValidStream.observe(viewLifecycleOwner, Observer { inputValid ->
                btnSubmit.isEnabled = inputValid
            })

            viewModel.completionEvent.observe(viewLifecycleOwner, Observer { navigationId ->
                findNavController().navigate(navigationId)
            })

            radioGroupQuestion1.setOnCheckedChangeListener { _, checkedId ->
                viewModel.questionFirstAnswer = when (checkedId) {
                    R.id.radio_btn_1_1 -> 1
                    R.id.radio_btn_1_2 -> 2
                    R.id.radio_btn_1_3 -> 3
                    else -> throw IllegalStateException("Invalid option selected")
                }
            }

            radioGroupQuestion2.setOnCheckedChangeListener { _, checkedId ->
                viewModel.questionSecondAnswer = when (checkedId) {
                    R.id.radio_btn_2_1 -> 1
                    R.id.radio_btn_2_2 -> 2
                    R.id.radio_btn_2_3 -> 3
                    R.id.radio_btn_2_4 -> 4
                    else -> throw IllegalStateException("Invalid option selected")
                }
            }

            with(editAdvice) {
                setHorizontallyScrolling(false)
                setLines(10)
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (!text.isNullOrBlank()) {
                            context?.hideKeyboard(view)
                            onSubmitClicked()
                        }
                        return@setOnEditorActionListener true
                    }
                    return@setOnEditorActionListener false
                }
                doAfterTextChanged { advice ->
                    viewModel.questionThirdAnswer = advice.toString()
                }
            }

            btnSubmit.setOnClickListener { onSubmitClicked() }
        }
    }

    private fun onSubmitClicked() {
        viewModel.submitEvaluation()
    }
}