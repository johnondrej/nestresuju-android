package cz.nestresuju.screens.about

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Dialog shown when user exits screen with message that is not sent.
 */
class FeedbackDiscardConfirmationDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(R.string.about_feedback_discard_warning)
            .setPositiveButton(R.string.general_discard) { _, _ ->
                (parentFragment as? OnConfirmedListener)?.onDiscardMessageConfirmed()
            }.setNegativeButton(R.string.general_back) { _, _ ->
                // do nothing
            }.create()
    }

    interface OnConfirmedListener {

        fun onDiscardMessageConfirmed()
    }
}