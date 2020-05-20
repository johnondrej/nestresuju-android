package cz.nestresuju.screens.program.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentProgram3ActivitiesSummaryBinding
import cz.nestresuju.screens.base.BaseArchFragment
import cz.nestresuju.views.program.third.ProgramThirdActivitySummaryView
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

/**
 * Screen with fourth part of third program.
 */
class ProgramThirdPhase4Fragment : BaseArchFragment<FragmentProgram3ActivitiesSummaryBinding>(),
    ProgramThirdSecondPartIntroDialogFragment1.OnProgramThirdIntro1ConfirmedListener,
    ProgramThirdSecondPartIntroDialogFragment2.OnProgramThirdIntro2ConfirmedListener {

    companion object {

        private const val TAG_DIALOG_INTRO_1 = "intro_1_dialog"
        private const val TAG_DIALOG_INTRO_2 = "intro_2_dialog"
    }

    override val viewModel by viewModel<ProgramThirdActivitiesSummaryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentProgram3ActivitiesSummaryBinding.inflate(inflater, container, false)
            .also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            progress.max = ProgramThirdConstants.PHASES
            progress.progress = 4

            viewModel.activitiesStream.observe(viewLifecycleOwner, Observer { activities ->
                layoutActivities.removeAllViews()
                val totalTime = activities.sumBy { (it.hours * 60) + it.minutes }.toFloat()

                activities.forEach { activity ->
                    val percentage = if (totalTime > 0) ((((activity.hours * 60) + activity.minutes) / totalTime) * 100).roundToInt() else 0

                    layoutActivities.addView(
                        ProgramThirdActivitySummaryView(requireContext()).apply {
                            txtName.text = activity.name
                            txtDuration.text = getString(R.string.program_3_activities_duration_format, activity.hours, activity.minutes)
                            txtDurationPercent.text = getString(R.string.program_3_activities_summary_percent, percentage)
                        }
                    )
                }
            })

            btnContinue.setOnClickListener {
                ProgramThirdSecondPartIntroDialogFragment1().show(childFragmentManager, TAG_DIALOG_INTRO_2)
            }

            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun onFirstIntroConfirmed() {
        ProgramThirdSecondPartIntroDialogFragment2().show(childFragmentManager, TAG_DIALOG_INTRO_1)
    }

    override fun onSecondIntroConfirmed() {
        findNavController().navigate(R.id.action_fragment_program_3_4_to_fragment_program_3_5)
    }
}