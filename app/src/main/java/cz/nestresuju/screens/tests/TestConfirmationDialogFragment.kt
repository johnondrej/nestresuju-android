package cz.nestresuju.screens.tests

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import cz.nestresuju.R

/**
 * Dialog shown after submitting input test.
 */
class TestConfirmationDialogFragment : DialogFragment() {

    companion object {

        private const val KEY_TEXT_RES = "text_resource"

        fun newInstance(@StringRes textRes: Int) = TestConfirmationDialogFragment().apply {
            arguments = bundleOf(KEY_TEXT_RES to textRes)
        }
    }

    private val textRes: Int by lazy(LazyThreadSafetyMode.NONE) { requireArguments().getInt(KEY_TEXT_RES) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.input_test_confirm_dialog_title)
            .setMessage(textRes)
            .setPositiveButton(R.string.general_begin) { _, _ ->
                (parentFragment as? OnTestConfirmedListener)?.onTestConfirmed()
            }.setCancelable(false)
            .create()
    }

    interface OnTestConfirmedListener {

        fun onTestConfirmed()
    }
}