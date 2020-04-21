package cz.nestresuju.screens.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.nestresuju.databinding.FragmentAboutTechnicalInfoBinding
import cz.nestresuju.screens.base.BaseFragment

/**
 * Fragment with technical info about appliaction.
 */
class AboutTechnicalInfoFragment : BaseFragment<FragmentAboutTechnicalInfoBinding>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentAboutTechnicalInfoBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            txtVersion.text = requireContext().packageManager.getPackageInfo(requireContext().packageName, 0).versionName
            txtDescription.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}