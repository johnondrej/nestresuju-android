package cz.nestresuju.screens.program.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import cz.nestresuju.databinding.DialogEditActivityBinding

/**
 * Dialog for editing activity's duration to the list.
 */
class ProgramThirdEditActivityDialogFragment : DialogFragment() {

    private var _binding: DialogEditActivityBinding? = null
    private val viewBinding: DialogEditActivityBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DialogEditActivityBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
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

        fun onActivityAdded(activityId: Long, durationHours: Int, durationMinutes: Int)
    }
}