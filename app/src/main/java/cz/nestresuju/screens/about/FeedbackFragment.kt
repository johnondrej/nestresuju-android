package cz.nestresuju.screens.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.common.interfaces.OnBackPressedListener
import cz.nestresuju.databinding.FragmentAboutFeedbackBinding
import cz.nestresuju.model.common.State
import cz.nestresuju.model.errors.handlers.InternetErrorsHandler
import cz.nestresuju.model.errors.handlers.UnknownErrorsHandler
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for sending app feedback.
 */
class FeedbackFragment :
    BaseArchFragment<FragmentAboutFeedbackBinding>(),
    FeedbackDiscardConfirmationDialog.OnConfirmedListener,
    FeedbackSentConfirmationDialog.OnConfirmedListener,
    OnBackPressedListener {

    companion object {

        private const val TAG_CONFIRMATION_DIALOG = "feedback_confirmation_dialog"
    }

    override val viewModel by viewModel<FeedbackViewModel>()

    private var dialogConfirmed = false

    override val errorHandlers = arrayOf(
        InternetErrorsHandler(),
        UnknownErrorsHandler()
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentAboutFeedbackBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            btnSend.setOnClickListener {
                editFeedback.text?.toString()?.let { feedback ->
                    viewModel.sendFeedback(feedback)
                }
            }

            editFeedback.doAfterTextChanged { feedback ->
                btnSend.isEnabled = !feedback.isNullOrBlank()
            }

            viewModel.completionStream.observe(viewLifecycleOwner, Observer { state ->
                val loading = state == State.Loading
                progress.visible = loading
                btnSend.isEnabled = !loading && !editFeedback.text?.toString().isNullOrBlank()
                editFeedback.isEnabled = !loading

                if (state is State.Loaded) {
                    FeedbackSentConfirmationDialog().show(childFragmentManager, TAG_CONFIRMATION_DIALOG)
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed(): Boolean {
        if (dialogConfirmed) {
            return false
        }

        if (!viewBinding.editFeedback.text?.toString().isNullOrBlank()) {
            FeedbackDiscardConfirmationDialog().show(childFragmentManager, TAG_CONFIRMATION_DIALOG)
            return true
        }
        return false
    }

    override fun onDiscardMessageConfirmed() {
        dialogConfirmed = true
        activity?.onBackPressed()
    }

    override fun onMessageSentConfirmed() {
        dialogConfirmed = true
        activity?.onBackPressed()
    }
}