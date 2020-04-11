package cz.nestresuju.screens.program.third

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Dialog shown when user selects he doesn't perform some activity in a day.
 */
class ProgramThirdHourChooseWarningDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(R.string.program_3_hours_warning_dialog_text)
            .setPositiveButton(R.string.general_understand) { _, _ ->  }
            .create()
    }
}