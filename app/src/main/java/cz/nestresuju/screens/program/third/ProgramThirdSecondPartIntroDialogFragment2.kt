package cz.nestresuju.screens.program.third

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Second intro dialog shown before second part of the third program.
 */
class ProgramThirdSecondPartIntroDialogFragment2 : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(R.string.program_3_goal_intro_2)
            .setPositiveButton(R.string.general_continue) { _, _ ->
                (parentFragment as? OnProgramThirdIntro2ConfirmedListener)?.onSecondIntroConfirmed()
            }.create()
    }

    interface OnProgramThirdIntro2ConfirmedListener {

        fun onSecondIntroConfirmed()
    }
}