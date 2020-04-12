package cz.nestresuju.screens.program.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentProgram3ActivitiesBinding
import cz.nestresuju.model.entities.domain.program.third.ProgramThirdResults
import cz.nestresuju.screens.base.BaseArchFragment
import cz.nestresuju.views.program.third.ProgramThirdHoursChooserView
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Screen with second part of third program.
 */
class ProgramThirdPhase2Fragment : BaseArchFragment<FragmentProgram3ActivitiesBinding>(),
    ProgramThirdEditActivityDialogFragment.OnActivityDurationChangedListener,
    ProgramThirdNewActivityDialogFragment.OnActivityAddedListener {

    companion object {

        private const val KEY_DIALOG_EDIT_ACTIVITY = "dialog_edit_activity"
        private const val KEY_DIALOG_ADD_ACTIVITY = "dialog_add_activity"
    }

    override val viewModel by viewModel<ProgramThirdActivitiesViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram3ActivitiesBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            progress.max = ProgramThirdConstants.PHASES
            progress.progress = 2

            viewModel.activitiesStream.observe(viewLifecycleOwner, Observer { activities ->
                layoutActivities.removeAllViews()

                activities.forEach { activity ->
                    layoutActivities.addView(ProgramThirdHoursChooserView(requireContext()).apply {
                        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

                        activityText = activity.name
                        btnText = activity.formattedTime()
                        removeEnabled = activity.userDefined

                        setOnChooseButtonClickedListener {
                            ProgramThirdEditActivityDialogFragment.newInstance(activity.name, activity.userDefined, activity.hours, activity.minutes)
                                .show(childFragmentManager, KEY_DIALOG_EDIT_ACTIVITY)
                        }

                        if (activity.userDefined) {
                            setOnRemoveButtonClickedListener {
                                viewModel.onActivityRemoved(activity.name)
                            }
                        }
                    })
                }
            })

            btnAddActivity.setOnClickListener {
                ProgramThirdNewActivityDialogFragment().show(childFragmentManager, KEY_DIALOG_ADD_ACTIVITY)
            }

            btnContinue.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_program_3_2_to_fragment_program_3_3)
            }

            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    override fun onActivityAdded(name: String, durationHours: Int, durationMinutes: Int) {
        viewModel.onActivityAdded(name, durationHours, durationMinutes)
    }

    override fun onActivityDurationChanged(activityId: String, userDefined: Boolean, durationHours: Int, durationMinutes: Int) {
        viewModel.onActivityTimeUpdated(activityId, userDefined, durationHours, durationMinutes)
    }

    private fun ProgramThirdResults.ActivityEntry.formattedTime() = String.format("%02d:%02d", hours, minutes)
}