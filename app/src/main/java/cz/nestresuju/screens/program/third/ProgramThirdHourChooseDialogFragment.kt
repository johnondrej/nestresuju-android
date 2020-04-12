package cz.nestresuju.screens.program.third

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Dialog for assigning time to an activity.
 */
class ProgramThirdHourChooseDialogFragment : DialogFragment() {

    companion object {

        private const val KEY_ACTIVITY_TYPE = "activity_type"

        fun newInstance(activity: ProgramThirdHourActivity) = ProgramThirdHourChooseDialogFragment().apply {
            arguments = bundleOf(KEY_ACTIVITY_TYPE to activity)
        }
    }

    private val activity: ProgramThirdHourActivity by lazy(LazyThreadSafetyMode.NONE) {
        arguments!!.getParcelable<ProgramThirdHourActivity>(KEY_ACTIVITY_TYPE)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val titleRes: Int
        val textRes: Int
        val textNegButtonRes: Int

        when (activity) {
            ProgramThirdHourActivity.BREAKFAST -> {
                titleRes = R.string.program_3_hours_choose_dialog_breakfast_title
                textRes = R.string.program_3_hours_choose_dialog_breakfast_text
                textNegButtonRes = R.string.program_3_hours_no_breakfast
            }
            ProgramThirdHourActivity.LUNCH -> {
                titleRes = R.string.program_3_hours_choose_dialog_lunch_title
                textRes = R.string.program_3_hours_choose_dialog_lunch_text
                textNegButtonRes = R.string.program_3_hours_no_lunch
            }
            ProgramThirdHourActivity.DINNER -> {
                titleRes = R.string.program_3_hours_choose_dialog_dinner_title
                textRes = R.string.program_3_hours_choose_dialog_dinner_text
                textNegButtonRes = R.string.program_3_hours_no_dinner
            }
            else -> throw IllegalArgumentException("Invalid activity type $activity")
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(titleRes)
            .setMessage(textRes)
            .setPositiveButton(R.string.program_3_hours_choose_dialog_choose_time) { _, _ ->
                (parentFragment as? OnActivityTimeSelectedListener)?.onTimeSelectRequested(activity)
            }.setNegativeButton(textNegButtonRes) { _, _ ->
                (parentFragment as? OnActivityTimeSelectedListener)?.onActivityNotInDay(activity)
            }
            .setCancelable(false)
            .create()
    }

    interface OnActivityTimeSelectedListener {

        fun onTimeSelectRequested(activity: ProgramThirdHourActivity)

        fun onActivityNotInDay(activity: ProgramThirdHourActivity)

        fun onTimeSet(activity: ProgramThirdHourActivity, hourOfDay: Int, minute: Int)
    }
}