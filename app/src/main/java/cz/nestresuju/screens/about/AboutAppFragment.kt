package cz.nestresuju.screens.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.changeStyle
import cz.nestresuju.databinding.FragmentCustomListBinding
import cz.nestresuju.screens.about.epoxy.AboutAppController
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutAppFragment : BaseArchFragment<FragmentCustomListBinding>() {

    override val viewModel by viewModel<AboutAppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            requireActivity().changeStyle(
                style = R.style.AboutStyle,
                primaryColor = R.color.aboutPrimary,
                primaryDarkColor = R.color.aboutPrimaryDark,
                accentColor = R.color.aboutAccent
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentCustomListBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding.customList) {
            list.setController(AboutAppController(
                onContactsClicked = { findNavController().navigate(R.id.action_fragment_about_app_to_fragment_about_app_contacts) },
                onResearchClicked = { findNavController().navigate(R.id.action_fragment_about_app_to_fragment_about_app_research) },
                onFeedbackClicked = { findNavController().navigate(R.id.action_fragment_about_app_to_fragment_about_app_feedback) },
                onTechnicalInfoClicked = { findNavController().navigate(R.id.action_fragment_about_app_to_fragment_about_app_technical_info) }
            ))
            list.requestModelBuild()
        }
    }
}
