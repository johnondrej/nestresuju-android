package cz.nestresuju.screens.program

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentProgramBinding
import cz.nestresuju.screens.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProgramFragment : BaseFragment<FragmentProgramBinding>() {

    override val viewModel by viewModel<ProgramViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgramBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            viewModel.text.observe(viewLifecycleOwner, Observer {
                textProgram.text = it
            })

            viewModel.progressStream.observe(viewLifecycleOwner, Observer { progress ->
                val programFirstProgress = progress.programFirstProgress

                textProgram.setOnClickListener {
                    val navController = findNavController()

                    // Build navigation backstack
                    navController.navigate(R.id.action_fragment_program_overview_to_fragment_program_1_1)
                    if (programFirstProgress >= 2) navController.navigate(R.id.action_fragment_program_1_1_to_fragment_program_1_2)
                    if (programFirstProgress >= 3) navController.navigate(R.id.action_fragment_program_1_2_to_fragment_program_1_3)
                    if (programFirstProgress >= 4) navController.navigate(R.id.action_fragment_program_1_3_to_fragment_program_1_4)
                    if (programFirstProgress >= 5) navController.navigate(R.id.action_fragment_program_1_4_to_fragment_program_1_5)
                    if (programFirstProgress >= 6) navController.navigate(R.id.action_fragment_program_1_5_to_fragment_program_1_6)
                }
            })
        }
    }
}
