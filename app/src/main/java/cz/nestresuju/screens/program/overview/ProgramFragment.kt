package cz.nestresuju.screens.program.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentCustomListBinding
import cz.nestresuju.model.entities.domain.program.first.ProgramFirstResults
import cz.nestresuju.model.entities.domain.program.second.ProgramSecondResults
import cz.nestresuju.model.entities.domain.program.third.ProgramThirdResults
import cz.nestresuju.screens.base.BaseArchFragment
import cz.nestresuju.screens.program.overview.epoxy.ProgramController
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProgramFragment : BaseArchFragment<FragmentCustomListBinding>() {

    private lateinit var controller: ProgramController

    override val viewModel by viewModel<ProgramViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentCustomListBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            customList.refreshLayout.isEnabled = false

            viewModel.screenStateStream.observe(viewLifecycleOwner, Observer { state ->
                controller = ProgramController(
                    onFirstProgramSelected = { onFirstProgramSelected(state.programFirstResults) },
                    onSecondProgramSelected = { onSecondProgramSelected(state.programSecondResults) },
                    onThirdProgramSelected = { onThirdProgramSelected(state.programThirdResults) },
                    onFourthProgramSelected = { onFourthProgramSelected() }
                ).also {
                    it.requestModelBuild()
                    customList.list.setController(it)
                }
            })
        }
    }

    private fun onFirstProgramSelected(results: ProgramFirstResults) {
        val navController = findNavController()

        if (results.programCompleted == null) {
            // Build navigation backstack
            navController.navigate(R.id.action_fragment_program_overview_to_fragment_program_1_1)
            if (results.progress >= 2) navController.navigate(R.id.action_fragment_program_1_1_to_fragment_program_1_2)
            if (results.progress >= 3) navController.navigate(R.id.action_fragment_program_1_2_to_fragment_program_1_3)
            if (results.progress >= 4) navController.navigate(R.id.action_fragment_program_1_3_to_fragment_program_1_4)
            if (results.progress >= 5) navController.navigate(R.id.action_fragment_program_1_4_to_fragment_program_1_5)
            if (results.progress >= 6) navController.navigate(R.id.action_fragment_program_1_5_to_fragment_program_1_6)
        } else {
            navController.navigate(R.id.action_fragment_program_overview_to_fragment_program_1_summary)
        }
    }

    private fun onSecondProgramSelected(results: ProgramSecondResults) {
        val navController = findNavController()

        if (results.progress < 1) {
            navController.navigate(R.id.action_fragment_program_overview_to_fragment_program_2_intro_1)
        } else {
            navController.navigate(R.id.action_fragment_program_overview_to_fragment_program_2_1)
        }
    }

    private fun onThirdProgramSelected(results: ProgramThirdResults) {
        val navController = findNavController()

        if (results.progress < 1) {
            navController.navigate(R.id.action_fragment_program_overview_to_fragment_program_3_intro)
        } else {
            // Build navigation backstack
            navController.navigate(R.id.fragment_program_3_1)
            if (results.progress >= 2) navController.navigate(R.id.action_fragment_program_3_1_to_fragment_program_3_2)
            if (results.progress >= 3) navController.navigate(R.id.action_fragment_program_3_2_to_fragment_program_3_3)
            if (results.progress >= 4) navController.navigate(R.id.action_fragment_program_3_3_to_fragment_program_3_4)
        }
    }

    private fun onFourthProgramSelected() {
        // TODO
        view?.let { Snackbar.make(it, "Program 4", Snackbar.LENGTH_LONG).show() }
    }
}
