package cz.nestresuju.screens.tests.output

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Dialog shown when user submits second part of output test.
 */
class OutputTestCompletedDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(R.string.output_test_2_confirmation_dialog_text)
            .setPositiveButton(R.string.general_finish) { _, _ ->
                (parentFragment as? OnCompletedListener)?.onOutputTestCompleted()
            }.setCancelable(false)
            .create()
    }

    interface OnCompletedListener {

        fun onOutputTestCompleted()
    }
}