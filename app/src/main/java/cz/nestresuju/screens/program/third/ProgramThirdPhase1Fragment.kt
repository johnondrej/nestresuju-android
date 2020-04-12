package cz.nestresuju.screens.program.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentProgram3HoursBinding
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Screen with first part of third program.
 */
class ProgramThirdPhase1Fragment : BaseArchFragment<FragmentProgram3HoursBinding>(),
    ProgramThirdHourChooseDialogFragment.OnActivityTimeSelectedListener {

    companion object {

        private const val TAG_FOOD_DIALOG = "dialog_food"
        private const val TAG_TIME_PICKER_DIALOG = "time_picker"
    }

    override val viewModel by viewModel<ProgramThirdTimetableViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram3HoursBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            progress.max = ProgramThirdConstants.PHASES
            progress.progress = 1

            chooserGettingUp.setOnChooseButtonClickedListener {
                showTimePicker(ProgramThirdHourActivity.GETTING_UP)
            }
            chooserBreakfast.setOnChooseButtonClickedListener {
                showTimePicker(ProgramThirdHourActivity.BREAKFAST)
            }
            chooserLunch.setOnChooseButtonClickedListener {
                showTimePicker(ProgramThirdHourActivity.LUNCH)
            }
            chooserDinner.setOnChooseButtonClickedListener {
                showTimePicker(ProgramThirdHourActivity.DINNER)
            }
            chooserGoingSleep.setOnChooseButtonClickedListener {
                showTimePicker(ProgramThirdHourActivity.GOING_SLEEP)
            }
            chooserSleeping.setOnChooseButtonClickedListener {
                showTimePicker(ProgramThirdHourActivity.SLEEPING)
            }

            viewModel.screenStateStream.observe(viewLifecycleOwner, Observer { screenState ->
                chooserGettingUp.btnText = screenState.gettingUpTime
                chooserBreakfast.btnText = screenState.breakfastTime
                chooserLunch.btnText = screenState.lunchTime
                chooserDinner.btnText = screenState.dinnerTime
                chooserGoingSleep.btnText = screenState.goingSleepTime
                chooserSleeping.btnText = screenState.sleepingTime

                btnContinue.isEnabled = screenState.canContinue
            })

            btnContinue.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_program_3_1_to_fragment_program_3_2)
            }
        }
    }

    override fun onTimeSelectRequested(activity: ProgramThirdHourActivity) {
        val defaultHour = when (activity) {
            ProgramThirdHourActivity.GETTING_UP -> 7
            ProgramThirdHourActivity.BREAKFAST -> 8
            ProgramThirdHourActivity.LUNCH -> 12
            ProgramThirdHourActivity.DINNER -> 19
            ProgramThirdHourActivity.GOING_SLEEP -> 22
            ProgramThirdHourActivity.SLEEPING -> 23
        }

        ProgramThirdHourChooseTimeDialogFragment.newInstance(activity, defaultHour).show(childFragmentManager, TAG_TIME_PICKER_DIALOG)
    }

    override fun onActivityNotInDay(activity: ProgramThirdHourActivity) {
        viewModel.onActivityTimeNotSet(activity)
        ProgramThirdHourChooseWarningDialogFragment().show(childFragmentManager, TAG_FOOD_DIALOG)
    }

    override fun onTimeSet(activity: ProgramThirdHourActivity, hourOfDay: Int, minute: Int) {
        viewModel.onActivityTimeSet(activity, hourOfDay, minute)
    }

    private fun showTimePicker(activity: ProgramThirdHourActivity) {
        when (activity) {
            ProgramThirdHourActivity.BREAKFAST, ProgramThirdHourActivity.LUNCH, ProgramThirdHourActivity.DINNER -> {
                ProgramThirdHourChooseDialogFragment.newInstance(activity).show(childFragmentManager, TAG_FOOD_DIALOG)
            }
            else -> {
                onTimeSelectRequested(activity)
            }
        }
    }
}