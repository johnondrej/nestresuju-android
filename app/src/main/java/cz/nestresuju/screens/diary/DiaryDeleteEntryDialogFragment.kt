package cz.nestresuju.screens.diary

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Confirmation dialog shown before deleting entry in diary.
 */
class DiaryDeleteEntryDialogFragment : DialogFragment() {

    companion object {

        private const val KEY_DIARY_ENTRY_ID = "entry_id"

        fun newInstance(entryId: Long) = DiaryDeleteEntryDialogFragment().apply {
            arguments = bundleOf(KEY_DIARY_ENTRY_ID to entryId)
        }
    }

    private val entryId: Long by lazy(LazyThreadSafetyMode.NONE) { arguments!!.getLong(KEY_DIARY_ENTRY_ID) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.diary_delete_note_title)
            .setMessage(R.string.diary_delete_note_text)
            .setPositiveButton(android.R.string.yes) { _, _ ->
                (parentFragment as? OnEntryDeleteConfirmedListener)?.onDiaryEntryDeleteConfirmed(entryId)
            }.setNegativeButton(android.R.string.cancel) { _, _ -> }
            .show()
    }

    interface OnEntryDeleteConfirmedListener {

        fun onDiaryEntryDeleteConfirmed(entryId: Long)
    }
}