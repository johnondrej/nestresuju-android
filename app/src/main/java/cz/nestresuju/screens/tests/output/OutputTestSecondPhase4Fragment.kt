package cz.nestresuju.screens.tests.output

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.hideKeyboard
import cz.nestresuju.databinding.FragmentOutputTest2Question4Binding
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.roundToInt

/**
 * Fragment for fourth phase of second part of output test.
 */
class OutputTestSecondPhase4Fragment : BaseArchFragment<FragmentOutputTest2Question4Binding>() {

    override val viewModel by sharedViewModel<OutputTestSecondViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentOutputTest2Question4Binding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            progress.max = OutputTestConstants.PHASES
            progress.progress = 4

            viewModel.screenStateStream.value!!.appRating?.let { rating ->
                ratingBar.rating = rating.toFloat()
            }
            editAnswer.setText(viewModel.screenStateStream.value!!.appRatingComment)

            ratingBar.setOnRatingChangeListener { _, rating ->
                viewModel.appRatingChanged(rating.roundToInt())
            }

            with(editAnswer) {
                setHorizontallyScrolling(false)
                setLines(10)
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        context?.hideKeyboard(view)
                    }
                    return@setOnEditorActionListener true
                }
                doAfterTextChanged { answer ->
                    viewModel.appRatingCommentChanged(answer?.toString() ?: "")
                }
            }

            btnContinue.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_output_test_2_phase_4_to_fragment_output_test_2_phase_5)
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            viewModel.screenStateStream.observe(viewLifecycleOwner, Observer { screenState ->
                btnContinue.isEnabled = screenState.appRating != null
            })
        }
    }
}