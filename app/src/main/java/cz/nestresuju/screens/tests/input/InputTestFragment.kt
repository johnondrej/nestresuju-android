package cz.nestresuju.screens.tests.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.common.interfaces.OnBackPressedListener
import cz.nestresuju.databinding.FragmentInputTestBinding
import cz.nestresuju.model.common.State
import cz.nestresuju.model.errors.InternetConnectionException
import cz.nestresuju.model.errors.handlers.InternetErrorsHandler
import cz.nestresuju.model.errors.handlers.UnknownErrorsHandler
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for showing input test after login.
 */
class InputTestFragment :
    BaseArchFragment<FragmentInputTestBinding>(),
    OnBackPressedListener,
    InputTestConfirmationDialogFragment.OnTestConfirmedListener {

    companion object {

        private const val TAG_CONFIRMATION_DIALOG = "confirmation_dialog"
    }

    override val viewModel by viewModel<InputTestViewModel>()

    override val errorHandlers = arrayOf(
        InternetErrorsHandler(),
        UnknownErrorsHandler()
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentInputTestBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            viewModel.viewStateStream.observe(viewLifecycleOwner, Observer { state ->
                layoutContent.visible = state is State.Loaded
                layoutInternetError.visible = (state as? State.Error)?.error is InternetConnectionException
                progress.visible = state == State.Loading

                if (state is State.Loaded) {
                    val viewState = state.data
                    questionsProgress.max = viewState.progress.totalQuestions
                    questionsProgress.progress = viewState.progress.currentQuestion + 1
                    questionView.question = viewState.question.text
                    btnContinue.text = if (!viewState.progress.isLast) getString(R.string.general_continue) else getString(R.string.general_finish)
                    btnContinue.isEnabled = viewState.answer != null
                    btnBack.visible = !viewState.progress.isFirst

                    if (viewState.answer != null) {
                        questionView.selectAnswer(viewState.answer)
                    } else {
                        questionView.unselectAll()
                    }

                    if (!viewState.progress.isLast) {
                        btnContinue.setOnClickListener {
                            viewModel.nextQuestion()
                        }
                    } else {
                        btnContinue.setOnClickListener {
                            viewModel.submitResults()
                        }
                    }
                }
            })

            viewModel.completionEvent.observe(viewLifecycleOwner, Observer {
                onTestSubmitted()
            })

            questionView.setOnAnswerSelectedListener { answer ->
                viewModel.selectAnswer(answer)
            }

            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }

            btnTryAgain.setOnClickListener {
                viewModel.tryAgain()
            }
        }
    }

    override fun onTestConfirmed() {
        findNavController().navigate(R.id.action_fragment_input_test_to_fragment_screening_test)
    }

    override fun onBackPressed(): Boolean {
        if (!viewModel.firstQuestionSelected()) {
            viewModel.previousQuestion()
            return true
        }
        return false
    }

    private fun onTestSubmitted() {
        InputTestConfirmationDialogFragment().show(childFragmentManager, TAG_CONFIRMATION_DIALOG)
    }
}