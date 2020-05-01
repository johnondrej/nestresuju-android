package cz.nestresuju.screens.about

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Dialog shown when user wants to cancel his account.
 */
class ResearchAccountCancelConfirmationDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.about_research_cancel_account)
            .setMessage(R.string.about_research_cancel_account_dialog_text)
            .setPositiveButton(R.string.about_research_cancel_account_button) { _, _ ->
                (parentFragment as? OnConfirmedListener)?.onAccountCancelConfirmedListener()
            }.setNegativeButton(R.string.general_back) { _, _ ->
                // do nothing
            }.create()
    }

    interface OnConfirmedListener {

        fun onAccountCancelConfirmedListener()
    }
}