package cz.nestresuju.screens.about

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Dialog shown when user feedback is successfully sent.
 */
class FeedbackSentConfirmationDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(R.string.about_feedback_sent_dialog)
            .setPositiveButton(R.string.general_continue) { _, _ ->
                (parentFragment as? OnConfirmedListener)?.onMessageSentConfirmed()
            }.setCancelable(false)
            .create()
    }

    interface OnConfirmedListener {

        fun onMessageSentConfirmed()
    }
}