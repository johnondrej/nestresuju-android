package cz.nestresuju.screens.program

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Dialog shown when user wants to open closed program.
 */
class ProgramClosedDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.program_closed_dialog_title)
            .setMessage(R.string.program_closed_dialog_text)
            .setPositiveButton(R.string.general_understand) { _, _ -> /* do nothing */ }
            .create()
    }
}