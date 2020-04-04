package cz.nestresuju.screens.tests.input

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Dialog shown after submitting input test.
 */
class InputTestConfirmationDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.input_test_confirm_dialog_title)
            .setMessage(R.string.input_test_confirm_dialog_text)
            .setPositiveButton(R.string.general_begin) { _, _ ->
                (parentFragment as? OnTestConfirmedListener)?.onTestConfirmed()
            }.setCancelable(false)
            .create()
    }

    interface OnTestConfirmedListener {

        fun onTestConfirmed()
    }
}