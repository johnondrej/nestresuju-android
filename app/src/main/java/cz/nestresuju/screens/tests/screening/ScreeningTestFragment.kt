package cz.nestresuju.screens.tests.screening

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.CheckBox
import android.widget.LinearLayout
import cz.nestresuju.common.extensions.dp
import cz.nestresuju.databinding.FragmentScreeningTestBinding
import cz.nestresuju.screens.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for showing screening test after login.
 */
class ScreeningTestFragment : BaseFragment<FragmentScreeningTestBinding>() {

    // TODO: remove UI mockup data and implement logic

    override val viewModel by viewModel<ScreeningTestViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentScreeningTestBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            layoutAnswers.removeAllViews()
            layoutAnswers.addAnswerCheckbox("Stěhování")
            layoutAnswers.addAnswerCheckbox("Studium")
            layoutAnswers.addAnswerCheckbox("Práce")
        }
    }

    private fun LinearLayout.addAnswerCheckbox(answerText: String) {
        val checkBox = CheckBox(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                topMargin = dp(4)
                bottomMargin = dp(4)
            }
            text = answerText
        }

        addView(checkBox)
    }
}