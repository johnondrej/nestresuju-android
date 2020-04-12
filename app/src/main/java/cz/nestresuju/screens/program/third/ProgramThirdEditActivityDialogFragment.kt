package cz.nestresuju.screens.program.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import cz.nestresuju.databinding.DialogEditActivityBinding

/**
 * Dialog for editing activity's duration to the list.
 */
class ProgramThirdEditActivityDialogFragment : DialogFragment() {

    companion object {

        private const val KEY_ACTIVITY_NAME = "activity_name"
        private const val KEY_ACTIVITY_USER_DEFINED = "is_user_defined"
        private const val KEY_ACTIVITY_DEFAULT_HOUR = "default_hour"
        private const val KEY_ACTIVITY_DEFAULT_MINUTE = "default_minute"

        fun newInstance(activityName: String, userDefined: Boolean, defaultHour: Int, defaultMinute: Int) =
            ProgramThirdEditActivityDialogFragment().apply {
                arguments = bundleOf(
                    KEY_ACTIVITY_NAME to activityName,
                    KEY_ACTIVITY_USER_DEFINED to userDefined,
                    KEY_ACTIVITY_DEFAULT_HOUR to defaultHour,
                    KEY_ACTIVITY_DEFAULT_MINUTE to defaultMinute
                )
            }
    }

    private val activityName: String by lazy(LazyThreadSafetyMode.NONE) { arguments!!.getString(KEY_ACTIVITY_NAME)!! }
    private val userDefined: Boolean by lazy(LazyThreadSafetyMode.NONE) { arguments!!.getBoolean(KEY_ACTIVITY_USER_DEFINED) }
    private val defaultHour: Int by lazy(LazyThreadSafetyMode.NONE) { arguments!!.getInt(KEY_ACTIVITY_DEFAULT_HOUR) }
    private val defaultMinute: Int by lazy(LazyThreadSafetyMode.NONE) { arguments!!.getInt(KEY_ACTIVITY_DEFAULT_MINUTE) }

    private var _binding: DialogEditActivityBinding? = null
    private val viewBinding: DialogEditActivityBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DialogEditActivityBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            pickerHours.maxValue = 23
            pickerHours.value = defaultHour
            pickerMinutes.maxValue = 3

            val minutesInput = arrayOf("0", "15", "30", "45")
            pickerMinutes.displayedValues = minutesInput
            pickerMinutes.value = minutesInput.indexOf(defaultMinute.toString())

            btnAdd.setOnClickListener {
                val hours = pickerHours.value
                val minutes = pickerMinutes.displayedValues[pickerMinutes.value].toInt()

                (parentFragment as? OnActivityDurationChangedListener)?.onActivityDurationChanged(activityName, userDefined, hours, minutes)
                dismiss()
            }

            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.attributes = dialog?.window?.attributes?.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnActivityDurationChangedListener {

        fun onActivityDurationChanged(activityId: String, userDefined: Boolean, durationHours: Int, durationMinutes: Int)
    }
}