package cz.nestresuju.screens.tests.screening

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.nestresuju.databinding.FragmentScreeningTestBinding
import cz.nestresuju.screens.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for showing screening test after login.
 */
class ScreeningTestFragment : BaseFragment<FragmentScreeningTestBinding>() {

    override val viewModel by viewModel<ScreeningTestViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentScreeningTestBinding.inflate(inflater, container, false).also { _binding = it }.root
    }
}