package cz.nestresuju.screens.program.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import cz.nestresuju.common.extensions.hideKeyboard
import cz.nestresuju.databinding.DialogAddActivityBinding

/**
 * Dialog for adding custom activities to the list.
 */
class ProgramThirdNewActivityDialogFragment : DialogFragment() {

    private var _binding: DialogAddActivityBinding? = null
    private val viewBinding: DialogAddActivityBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DialogAddActivityBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            pickerHours.maxValue = 23
            pickerMinutes.maxValue = 3
            pickerMinutes.displayedValues = arrayOf("0", "15", "30", "45")

            editActivityName.doAfterTextChanged { text ->
                btnAdd.isEnabled = !text.isNullOrBlank()
            }
            editActivityName.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    context?.hideKeyboard(view)
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

            btnAdd.isEnabled = !editActivityName.text.isNullOrBlank()
            btnAdd.setOnClickListener {
                val hours = pickerHours.value
                val minutes = pickerMinutes.displayedValues[pickerMinutes.value].toInt()

                (parentFragment as? OnActivityAddedListener)?.onActivityAdded(editActivityName.text.toString(), hours, minutes)
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

    interface OnActivityAddedListener {

        fun onActivityAdded(name: String, durationHours: Int, durationMinutes: Int)
    }
}