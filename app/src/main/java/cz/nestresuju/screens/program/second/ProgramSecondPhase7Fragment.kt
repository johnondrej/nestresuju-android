package cz.nestresuju.screens.program.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentProgram2RelaxationBinding
import cz.nestresuju.screens.base.BaseArchFragment
import cz.nestresuju.screens.program.ProgramSubmittedDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment with seventh part of first program.
 */
class ProgramSecondPhase7Fragment :
    BaseArchFragment<FragmentProgram2RelaxationBinding>(),
    ProgramSubmittedDialogFragment.OnProgramSubmittedListener {

    companion object {

        private const val TAG_CONFIRMATION_DIALOG = "confirmation_dialog"
    }

    override val viewModel by viewModel<ProgramSecondRelaxationViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram2RelaxationBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            progress.max = ProgramSecondConstants.PHASES
            progress.progress = 7

            btnFinish.setOnClickListener {
                onFinishClicked()
            }
        }
    }

    override fun onProgramSubmitted() {
        findNavController().popBackStack(R.id.navigation_program, false)
    }

    private fun onFinishClicked() {
        viewModel.submitRelaxationDuration()

        ProgramSubmittedDialogFragment.newInstance(textRes = R.string.program_2_submitted_dialog_text)
            .show(childFragmentManager, TAG_CONFIRMATION_DIALOG)
    }
}