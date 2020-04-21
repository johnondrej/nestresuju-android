package cz.nestresuju.screens.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.nestresuju.databinding.FragmentAboutFeedbackBinding
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for sending app feedback.
 */
class FeedbackFragment : BaseArchFragment<FragmentAboutFeedbackBinding>() {

    override val viewModel by viewModel<FeedbackViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentAboutFeedbackBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}