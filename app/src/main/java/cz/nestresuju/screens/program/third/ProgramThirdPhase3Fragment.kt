package cz.nestresuju.screens.program.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentProgram3ActivitiesSummaryIntroBinding
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Screen with third part of third program.
 */
class ProgramThirdPhase3Fragment : BaseArchFragment<FragmentProgram3ActivitiesSummaryIntroBinding>() {

    override val viewModel by viewModel<ProgramThirdActivitiesSummaryIntroViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram3ActivitiesSummaryIntroBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            progress.max = ProgramThirdConstants.PHASES
            progress.progress = 3

            btnContinue.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_program_3_3_to_fragment_program_3_4)
            }

            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }
}