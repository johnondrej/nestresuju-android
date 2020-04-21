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
class ResearchFragment : BaseArchFragment<FragmentCustomListBinding>() {

    override val viewModel by viewModel<ResearchViewModel>()

    private lateinit var controller: ResearchController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        controller = ResearchController()
        return FragmentCustomListBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.customList) {
            list.setController(controller)
            emptyTextResource = R.string.about_research_empty

            viewModel.researchInfoStream.observe(viewLifecycleOwner, Observer { state ->
                val data = (state as? State.Loaded)?.data

                contentVisible = state is State.Loaded
                refreshLayout.isRefreshing = state is State.Loading
                emptyTextVisible = data?.text?.isEmpty() == true && data.subsections.isEmpty() == true

                if (state is State.Loaded) {
                    controller.researchInfo = state.data
                }
            })

            refreshLayout.setOnRefreshListener {
                viewModel.fetchResearchInfo()
            }
        }
    }
}