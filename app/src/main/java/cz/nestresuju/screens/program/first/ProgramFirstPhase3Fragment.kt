package cz.nestresuju.screens.program.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.databinding.FragmentProgram1ScaleBinding
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment with third part of first program.
 */
class ProgramFirstPhase3Fragment : BaseArchFragment<FragmentProgram1ScaleBinding>() {

    override val viewModel by viewModel<ProgramFirstSatisfiabilityViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram1ScaleBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            txtQuestion.text = getString(R.string.program_1_question_3)
            btnContinue.setOnClickListener { onContinueClicked() }
            btnBack.setOnClickListener { activity?.onBackPressed() }

            seekSatisfiability.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        viewModel.onScaleChanged(progress + 1)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            viewModel.scaleStream.observe(viewLifecycleOwner, Observer { satisfiability ->
                val satisfiabilityValid = satisfiability >= ProgramFirstSatisfiabilityViewModel.MIN_SATISFIABILITY_ALLOWED

                seekSatisfiability.seekBar.progress = satisfiability - 1
                btnContinue.isEnabled = satisfiabilityValid
                txtScaleWarning?.visible = !satisfiabilityValid
            })
        }
    }

    private fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_1_3_to_fragment_program_1_4)
    }
}