package cz.nestresuju.screens.program.first

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
import cz.nestresuju.databinding.FragmentProgram1OverviewBinding
import cz.nestresuju.screens.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment with sixth part of first program.
 */
class ProgramFirstPhase6Fragment :
    BaseFragment<FragmentProgram1OverviewBinding>(),
    ProgramFirstSubmittedDialogFragment.OnFirstProgramSubmittedListener {

    companion object {

        private const val TAG_CONFIRMATION_DIALOG = "confirmation_dialog"
    }

    override val viewModel by viewModel<ProgramFirstOverviewViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram1OverviewBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            with(editSummary) {
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (!editSummary.text.isNullOrBlank()) {
                            context?.hideKeyboard(view)
                            onSubmitClicked(editSummary.text.toString())
                        }
                        return@setOnEditorActionListener true
                    }
                    return@setOnEditorActionListener false
                }
                doAfterTextChanged { summary ->
                    viewModel.onSummaryChanged(summary.toString())
                }
            }

            viewModel.resultsStream.observe(viewLifecycleOwner, Observer { results ->
                summaryTarget.answer = results.target
                summaryCompletion.answer = results.completion
                summarySatisfiability.answer = "${results.satisfiability} / 10"
                summaryReason.answer = results.reason
                summaryDeadline.answer = results.deadline
            })

            viewModel.summaryStream.observe(viewLifecycleOwner, Observer { summary ->
                if (summary.isNotBlank() && editSummary.text.isNullOrBlank()) {
                    editSummary.setText(summary)
                }

                btnFinish.isEnabled = summary.isNotBlank()
                btnFinish.setOnClickListener { onSubmitClicked(summary) }
            })

            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun onFirstProgramSubmitted() {
        findNavController().popBackStack(R.id.navigation_program, false)
    }

    private fun onSubmitClicked(summary: String) {
        viewModel.submitResults()

        ProgramFirstSubmittedDialogFragment.newInstance(summary).show(childFragmentManager, TAG_CONFIRMATION_DIALOG)
    }
}