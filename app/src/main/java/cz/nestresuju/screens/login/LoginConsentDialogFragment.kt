package cz.nestresuju.screens.login

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Alert dialog with informed consent shown after first login.
 */
class LoginConsentDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.login_consent_title)
            .setMessage(R.string.login_consent_text)
            .setPositiveButton(R.string.next) { _, _ ->
                (parentFragment as? OnConfirmedListener)?.onConsentConfirmed(confirmed = true)
            }.setNegativeButton(android.R.string.cancel) { _, _ ->
                (parentFragment as? OnConfirmedListener)?.onConsentConfirmed(confirmed = false)
            }.create()
    }

    interface OnConfirmedListener {

        fun onConsentConfirmed(confirmed: Boolean)
    }
}