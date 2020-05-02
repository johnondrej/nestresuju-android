package cz.nestresuju.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.bottomPadding
import cz.nestresuju.common.extensions.dp
import cz.nestresuju.databinding.FragmentCustomListBinding
import cz.nestresuju.model.common.State
import cz.nestresuju.screens.base.BaseArchFragment
import cz.nestresuju.screens.home.epoxy.HomeController
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseArchFragment<FragmentCustomListBinding>() {

    private lateinit var controller: HomeController

    override val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        controller = HomeController(
            applicationContext = requireContext().applicationContext,
            onItemClicked = { destination -> findNavController().navigate(destination.navAction) }
        )
        return FragmentCustomListBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            customList.list.setController(controller)
            customList.list.bottomPadding = requireContext().dp(8)
            customList.refreshLayout.isEnabled = false

            viewModel.screenStateStream.observe(viewLifecycleOwner, Observer { state ->
                customList.contentVisible = state is State.Loaded
                customList.progressVisible = state is State.Loading

                if (state is State.Loaded) {
                    controller.items = state.data
                }
            })
        }
    }

    private val HomeViewModel.Destination.navAction: Int
        get() = when (this) {
            HomeViewModel.Destination.PROGRAM -> R.id.action_fragment_home_to_fragment_program
            HomeViewModel.Destination.DIARY -> R.id.action_fragment_home_to_fragment_diary
            HomeViewModel.Destination.LIBRARY -> R.id.action_fragment_home_to_fragment_library
        }
}
