package cz.nestresuju.screens.tests.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentInputTestBinding
import cz.nestresuju.screens.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment for showing input test after login.
 */
class InputTestFragment : BaseFragment<FragmentInputTestBinding>() {

    override val viewModel by viewModel<InputTestViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentInputTestBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.txtInput.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_input_test_to_fragment_screening_test)
        }
    }
}