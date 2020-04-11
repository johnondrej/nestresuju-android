package cz.nestresuju.screens.program.third

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment

/**
 * Time picker dialog.
 */
class ProgramThirdHourChooseTimeDialogFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    companion object {

        private const val KEY_DEFAULT_HOUR = "default_hour"
        private const val KEY_ACTIVITY_TYPE = "activity_type"

        fun newInstance(activity: ProgramThirdHourActivity, defaultHour: Int) = ProgramThirdHourChooseTimeDialogFragment().apply {
            arguments = bundleOf(
                KEY_ACTIVITY_TYPE to activity,
                KEY_DEFAULT_HOUR to defaultHour
            )
        }
    }

    private val defaultHour: Int by lazy(LazyThreadSafetyMode.NONE) { arguments!!.getInt(KEY_DEFAULT_HOUR) }
    private val activity: ProgramThirdHourActivity by lazy(LazyThreadSafetyMode.NONE) {
        arguments!!.getParcelable<ProgramThirdHourActivity>(KEY_ACTIVITY_TYPE)!!
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(requireContext(), this, defaultHour, 0, true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        (parentFragment as? ProgramThirdHourChooseDialogFragment.OnActivityTimeSelectedListener)?.onTimeSet(activity, hourOfDay, minute)
    }
}