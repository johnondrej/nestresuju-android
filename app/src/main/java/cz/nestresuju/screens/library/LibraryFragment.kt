package cz.nestresuju.screens.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cz.nestresuju.R
import cz.nestresuju.common.interfaces.OnBackPressedListener
import cz.nestresuju.databinding.FragmentCustomListBinding
import cz.nestresuju.model.common.State
import cz.nestresuju.screens.base.BaseArchFragment
import cz.nestresuju.screens.library.epoxy.LibraryController
import org.koin.androidx.viewmodel.ext.android.viewModel

class LibraryFragment : BaseArchFragment<FragmentCustomListBinding>(), OnBackPressedListener {

    override val viewModel by viewModel<LibraryViewModel>()

    private lateinit var controller: LibraryController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        controller = LibraryController(
            onSubsectionClicked = { subsection -> viewModel.onSubsectionSelected(subsection) }
        )
        return FragmentCustomListBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.customList) {
            emptyTextResource = R.string.library_empty
            list.setController(controller)

            viewModel.sectionStream.observe(viewLifecycleOwner, Observer { state ->
                contentVisible = state is State.Loaded
                refreshLayout.isRefreshing = state is State.Loading
                emptyTextVisible = state is State.Empty

                if (state is State.Loaded) {
                    controller.librarySection = state.data
                }
            })

            refreshLayout.setOnRefreshListener {
                viewModel.fetchLibraryContent()
            }
        }
    }

    override fun onBackPressed(): Boolean {
        return viewModel.onParentSectionSelected()
    }
}
