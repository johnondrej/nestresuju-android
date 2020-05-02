package cz.nestresuju.screens.tests.output

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.databinding.FragmentOutputTest2Question5Binding
import cz.nestresuju.model.common.State
import cz.nestresuju.model.errors.handlers.InternetErrorsHandler
import cz.nestresuju.model.errors.handlers.UnknownErrorsHandler
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Fragment for fifth phase of second part of output test.
 */
class OutputTestSecondPhase5Fragment : BaseArchFragment<FragmentOutputTest2Question5Binding>(),
    OutputTestCompletedDialogFragment.OnCompletedListener {

    companion object {

        private const val TAG_CONFIRMATION_DIALOG = "output_test_confirmation_dialog"
    }

    override val viewModel by sharedViewModel<OutputTestSecondViewModel>()

    override val errorHandlers = arrayOf(
        InternetErrorsHandler(),
        UnknownErrorsHandler()
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentOutputTest2Question5Binding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            progress.max = OutputTestConstants.PHASES
            progress.progress = 5

            viewModel.screenStateStream.value!!.recommendation?.let { recommendation ->
                editFeedback.setText(recommendation)
            }

            with(editFeedback) {
                setHorizontallyScrolling(false)
                setLines(10)
                doAfterTextChanged { feedback ->
                    viewModel.recommendationChanged(feedback?.toString() ?: "")
                }
            }

            btnSend.setOnClickListener {
                viewModel.sendResults()
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            viewModel.completionStream.observe(viewLifecycleOwner, Observer { state ->
                val loading = state == State.Loading
                loadingProgress.visible = loading
                btnSend.isEnabled = !loading
                btnBack.isEnabled = !loading
                editFeedback.isEnabled = !loading

                if (state is State.Loaded) {
                    OutputTestCompletedDialogFragment().show(childFragmentManager, TAG_CONFIRMATION_DIALOG)
                }
            })
        }
    }

    override fun onOutputTestCompleted() {
        findNavController().popBackStack(R.id.navigation_program, false)
    }
}