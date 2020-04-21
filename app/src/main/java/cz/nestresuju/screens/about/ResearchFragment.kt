package cz.nestresuju.screens.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.nestresuju.databinding.FragmentCustomListBinding
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
        return FragmentCustomListBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.customList) {
            controller = ResearchController().also {
                list.setController(it)
                it.requestModelBuild()
            }
        }
    }
}