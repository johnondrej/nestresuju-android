package cz.nestresuju.screens.program.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import cz.nestresuju.common.extensions.hideKeyboard
import cz.nestresuju.databinding.FragmentProgram1OverviewBinding
import cz.nestresuju.screens.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment with sixth part of first program.
 */
class ProgramFirstPhase6Fragment : BaseFragment<FragmentProgram1OverviewBinding>() {

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
                            onSubmitClicked()
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
            })

            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    private fun onSubmitClicked() {
        // TODO
    }
}