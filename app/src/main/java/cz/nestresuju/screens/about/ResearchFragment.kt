package cz.nestresuju.screens.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentCustomListBinding
import cz.nestresuju.model.common.State
import cz.nestresuju.screens.about.epoxy.ResearchController
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment with information about research.
 */
class ResearchFragment : BaseArchFragment<FragmentCustomListBinding>(), ResearchAccountCancelConfirmationDialog.OnConfirmedListener {

    companion object {

        private const val TAG_ACCOUNT_CANCEL_DIALOG = "account_cancel_dialog"
    }

    override val viewModel by viewModel<ResearchViewModel>()

    private lateinit var controller: ResearchController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        controller = ResearchController(
            onCancelAccountButtonClicked = { onCancelAccountButtonClicked() }
        )
        return FragmentCustomListBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.customList) {
            list.setController(controller)
            emptyTextResource = R.string.about_research_empty

            viewModel.screenStateStream.observe(viewLifecycleOwner, Observer { screenState ->
                val data = (screenState.researchInfoState as? State.Loaded)?.data

                contentVisible = data?.text?.isNotEmpty() == true && screenState.cancelAccountState != State.Loading
                refreshLayout.isRefreshing = screenState.researchInfoState == State.Loading || screenState.cancelAccountState == State.Loading
                emptyTextVisible = data?.text?.isEmpty() == true && screenState.cancelAccountState != State.Loading

                if (screenState.researchInfoState is State.Loaded) {
                    controller.researchInfo = screenState.researchInfoState.data
                }
            })

            refreshLayout.setOnRefreshListener {
                viewModel.fetchResearchInfo()
            }
        }
    }

    private fun onCancelAccountButtonClicked() {
        ResearchAccountCancelConfirmationDialog().show(childFragmentManager, TAG_ACCOUNT_CANCEL_DIALOG)
    }

    override fun onAccountCancelConfirmedListener() {
        viewModel.cancelAccount()
    }
}