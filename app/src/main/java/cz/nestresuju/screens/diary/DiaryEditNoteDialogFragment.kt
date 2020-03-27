package cz.nestresuju.screens.diary

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import cz.nestresuju.databinding.DialogEditNoteBinding

/**
 * [DialogFragment] that allows user editing notes.
 */
class DiaryEditNoteDialogFragment : DialogFragment() {

    companion object {

        private const val KEY_DIARY_ENTRY_ID = "entry_id"
        private const val KEY_DIARY_ENTRY_TEXT = "entry_text"

        fun newInstance(entryId: Long, entryText: String) = DiaryEditNoteDialogFragment().apply {
            arguments = bundleOf(
                KEY_DIARY_ENTRY_ID to entryId,
                KEY_DIARY_ENTRY_TEXT to entryText
            )
        }
    }

    private var _binding: DialogEditNoteBinding? = null
    private val viewBinding: DialogEditNoteBinding
        get() = _binding!!

    private val entryId: Long by lazy(LazyThreadSafetyMode.NONE) { arguments!!.getLong(KEY_DIARY_ENTRY_ID) }
    private val entryText: String by lazy(LazyThreadSafetyMode.NONE) { arguments!!.getString(KEY_DIARY_ENTRY_TEXT)!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DialogEditNoteBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            editText.setText(entryText)
            toggleSaveButtonState(entryText)
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                }
            }
            editText.requestFocus()

            editText.doAfterTextChanged { text ->
                toggleSaveButtonState(text.toString())
            }

            btnSave.setOnClickListener {
                (parentFragment as? OnEntryEditConfirmedListener)?.onDiaryEntryEditConfirmed(entryId, editText.text.toString())
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

    private fun toggleSaveButtonState(text: String) {
        viewBinding.btnSave.isEnabled = !text.isBlank()
    }

    interface OnEntryEditConfirmedListener {

        fun onDiaryEntryEditConfirmed(entryId: Long, modifiedText: String)
    }
}