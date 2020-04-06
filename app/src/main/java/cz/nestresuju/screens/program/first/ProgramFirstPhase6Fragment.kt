package cz.nestresuju.screens.program.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
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
            seekSatisfiability.seekBar.isEnabled = false

            editSummary.doAfterTextChanged { summary ->
                viewModel.onSummaryChanged(summary.toString())
            }

            viewModel.summaryStream.observe(viewLifecycleOwner, Observer { answer ->
                btnFinish.isEnabled = answer.isNotBlank()
            })

            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }
}