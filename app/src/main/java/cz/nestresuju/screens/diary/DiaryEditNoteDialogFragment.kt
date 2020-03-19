package cz.nestresuju.screens.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import cz.nestresuju.databinding.DialogEditNoteBinding

/**
 * [DialogFragment] that allows user editing notes.
 */
class DiaryEditNoteDialogFragment : DialogFragment() {

    private var _binding: DialogEditNoteBinding? = null
    private val viewBinding: DialogEditNoteBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DialogEditNoteBinding.inflate(inflater, container, false).also { _binding = it }.root
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
}