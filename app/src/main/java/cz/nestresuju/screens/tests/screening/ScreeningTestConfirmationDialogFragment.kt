package cz.nestresuju.screens.tests.screening

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Dialog shown after submitting screening test.
 */
class ScreeningTestConfirmationDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(R.string.screening_test_confirm_dialog_text)
            .setPositiveButton(R.string.screening_test_confirm_dialog_button) { _, _ ->
                (parentFragment as? OnTestConfirmedListener)?.onTestConfirmed()
            }.setCancelable(false)
            .create()
    }

    interface OnTestConfirmedListener {

        fun onTestConfirmed()
    }
}