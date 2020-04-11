package cz.nestresuju.screens.program

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Dialog shown when user submits some program.
 */
class ProgramSubmittedDialogFragment : DialogFragment() {

    companion object {

        private const val KEY_TEXT_RES = "text_resource"

        fun newInstance(@StringRes textRes: Int) = ProgramSubmittedDialogFragment().apply {
            arguments = bundleOf(KEY_TEXT_RES to textRes)
        }
    }

    private val textRes: Int by lazy(LazyThreadSafetyMode.NONE) { arguments!!.getInt(KEY_TEXT_RES) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(textRes)
            .setPositiveButton(R.string.general_continue) { _, _ ->
                (parentFragment as? OnProgramSubmittedListener)?.onProgramSubmitted()
            }.setCancelable(false)
            .create()
    }

    interface OnProgramSubmittedListener {

        fun onProgramSubmitted()
    }
}