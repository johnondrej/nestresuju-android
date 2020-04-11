package cz.nestresuju.screens.program.first

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Dialog shown when user submits first program.
 */
class ProgramFirstSubmittedDialogFragment : DialogFragment() {

    companion object {

        private const val KEY_GOAL = "goal"

        fun newInstance(goal: String) = ProgramFirstSubmittedDialogFragment().apply {
            arguments = bundleOf(KEY_GOAL to goal)
        }
    }

    private val goal: String by lazy(LazyThreadSafetyMode.NONE) { arguments!!.getString(KEY_GOAL, "") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.program_1_submitted_dialog_text, goal))
            .setPositiveButton(R.string.general_continue) { _, _ ->
                (parentFragment as? OnFirstProgramSubmittedListener)?.onFirstProgramSubmitted()
            }.setCancelable(false)
            .create()
    }

    interface OnFirstProgramSubmittedListener {

        fun onFirstProgramSubmitted()
    }
}