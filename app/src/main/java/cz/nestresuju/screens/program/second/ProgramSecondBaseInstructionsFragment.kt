package cz.nestresuju.screens.program.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.nestresuju.databinding.FragmentProgram2InstructionsBinding
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Base fragment with instructions in second program's phase.
 */
abstract class ProgramSecondBaseInstructionsFragment : BaseArchFragment<FragmentProgram2InstructionsBinding>() {

    protected abstract val instructionsRes: Int
    protected abstract val phaseProgress: Int

    override val viewModel by viewModel<ProgramSecondInstructionsViewModel> { parametersOf(phaseProgress) }

    protected abstract fun onContinueClicked()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram2InstructionsBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateProgress()

        with(viewBinding) {
            progress.max = ProgramSecondConstants.PHASES
            progress.progress = phaseProgress
            txtInstructions.text = getString(instructionsRes)
            btnContinue.setOnClickListener { onContinueClicked() }
            btnBack.setOnClickListener { activity?.onBackPressed() }
        }
    }
}