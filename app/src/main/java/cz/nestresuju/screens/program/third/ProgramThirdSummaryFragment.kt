package cz.nestresuju.screens.program.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentProgram1SummaryBinding
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment with summary of third program.
 */
class ProgramThirdSummaryFragment : BaseArchFragment<FragmentProgram1SummaryBinding>() {

    override val viewModel by viewModel<ProgramThirdSummaryViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram1SummaryBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            title.text = getString(R.string.program_3_summary_title)

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