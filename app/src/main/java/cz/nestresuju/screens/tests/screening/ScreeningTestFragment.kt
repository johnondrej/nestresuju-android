package cz.nestresuju.screens.tests.screening

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import cz.nestresuju.MainActivity
import cz.nestresuju.R
import cz.nestresuju.common.extensions.dp
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.common.interfaces.OnBackPressedListener
import cz.nestresuju.databinding.FragmentScreeningTestBinding
import cz.nestresuju.model.common.State
import cz.nestresuju.model.entities.domain.tests.screening.ScreeningTestOption
import cz.nestresuju.model.errors.InternetConnectionException
import cz.nestresuju.model.errors.handlers.InternetErrorsHandler
import cz.nestresuju.model.errors.handlers.UnknownErrorsHandler
import cz.nestresuju.screens.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for showing screening test after login.
 */
class ScreeningTestFragment :
    BaseFragment<FragmentScreeningTestBinding>(),
    OnBackPressedListener,
    ScreeningTestConfirmationDialogFragment.OnTestConfirmedListener {

    companion object {

        private const val TAG_CONFIRMATION_DIALOG = "confirmation_dialog"
    }

    private var ignoreIsCheckedListeners = false

    override val viewModel by viewModel<ScreeningTestViewModel>()

    override val errorHandlers = arrayOf(
        InternetErrorsHandler(),
        UnknownErrorsHandler()
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentScreeningTestBinding.inflate(inflater, container, false).also { _binding = it }.root
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
                    optionsProgress.max = viewState.progress.totalPages
                    optionsProgress.progress = viewState.progress.currentPage + 1
                    btnContinue.text = if (!viewState.progress.isLast) getString(R.string.general_continue) else getString(R.string.general_finish)
                    btnBack.visible = !viewState.progress.isFirst

                    if (layoutOptions.childCount == 0) {
                        viewState.options.forEach { option ->
                            layoutOptions.addOptionCheckbox(option, optionSelected = option.id in viewState.selectedOptionIds)
                        }
                    }

                    if (!viewState.progress.isLast) {
                        btnContinue.setOnClickListener {
                            viewModel.nextPage()
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

            btnBack.setOnClickListener {
                activity?.onBackPressed()
            }

            btnTryAgain.setOnClickListener {
                viewModel.tryAgain()
            }
        }
    }

    override fun onTestConfirmed() {
        MainActivity.launch(requireContext())
        activity?.finish()
    }

    private fun onTestSubmitted() {
        ScreeningTestConfirmationDialogFragment().show(childFragmentManager, TAG_CONFIRMATION_DIALOG)
    }

    private fun LinearLayout.addOptionCheckbox(option: ScreeningTestOption, optionSelected: Boolean) {
        val checkBox = CheckBox(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                topMargin = dp(4)
                bottomMargin = dp(4)
            }

            text = option.text

            ignoreIsCheckedListeners = true
            isChecked = optionSelected
            ignoreIsCheckedListeners = false

            setOnCheckedChangeListener { _, isChecked ->
                if (ignoreIsCheckedListeners) {
                    return@setOnCheckedChangeListener
                }

                if (isChecked) {
                    viewModel.selectOption(option.id)
                } else {
                    viewModel.unselectOption(option.id)
                }
            }
        }

        addView(checkBox)
    }

    override fun onBackPressed(): Boolean {
        if (!viewModel.firstPageSelected()) {
            viewModel.previousPage()
            return true
        }
        return false
    }
}