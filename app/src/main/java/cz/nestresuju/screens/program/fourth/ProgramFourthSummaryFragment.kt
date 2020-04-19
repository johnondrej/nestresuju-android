package cz.nestresuju.screens.program.fourth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentProgram4SummaryBinding
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment with summary of fourth program.
 */
class ProgramFourthSummaryFragment : BaseArchFragment<FragmentProgram4SummaryBinding>() {

    override val viewModel by viewModel<ProgramFourthSummaryViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram4SummaryBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            viewModel.evaluationStream.observe(viewLifecycleOwner, Observer { evaluationTextRes ->
                txtEvaluation.text = getString(evaluationTextRes)
            })

            btnFinish.setOnClickListener {
                findNavController().popBackStack(R.id.navigation_program, false)
            }
        }
    }
}