package cz.nestresuju.screens.program.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.nestresuju.databinding.FragmentProgram1SummaryBinding
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment with summary of first program.
 */
class ProgramFirstSummaryFragment : BaseArchFragment<FragmentProgram1SummaryBinding>() {

    override val viewModel by viewModel<ProgramFirstSummaryViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram1SummaryBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            viewModel.resultsStream.observe(viewLifecycleOwner, Observer { results ->
                summaryTarget.answer = results.target
                summaryCompletion.answer = results.completion
                summarySatisfiability.answer = "${results.satisfiability} / 10"
                summaryReason.answer = results.reason
                summaryDeadline.answer = results.deadline
                summaryTargetShort.answer = results.summarizedTarget
            })
        }
    }
}