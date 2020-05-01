package cz.nestresuju.screens.program.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.bottomPadding
import cz.nestresuju.common.extensions.changeStyle
import cz.nestresuju.common.extensions.dp
import cz.nestresuju.databinding.FragmentCustomListBinding
import cz.nestresuju.model.common.State
import cz.nestresuju.model.entities.domain.program.ProgramId
import cz.nestresuju.model.entities.domain.program.first.ProgramFirstResults
import cz.nestresuju.model.entities.domain.program.fourth.ProgramFourthResults
import cz.nestresuju.model.entities.domain.program.overview.ProgramOverview
import cz.nestresuju.model.entities.domain.program.second.ProgramSecondResults
import cz.nestresuju.model.entities.domain.program.third.ProgramThirdResults
import cz.nestresuju.screens.base.BaseArchFragment
import cz.nestresuju.screens.program.ProgramClosedDialogFragment
import cz.nestresuju.screens.program.evaluation.ProgramEvaluationFragment
import cz.nestresuju.screens.program.overview.epoxy.ProgramController
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProgramFragment : BaseArchFragment<FragmentCustomListBinding>() {

    companion object {

        private const val TAG_PROGRAM_CLOSED_DIALOG = "program_closed_dialog"
    }

    private lateinit var controller: ProgramController

    override val viewModel by viewModel<ProgramViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            requireActivity().changeStyle(
                style = R.style.ProgramStyle,
                primaryColor = R.color.programPrimary,
                primaryDarkColor = R.color.programPrimaryDark,
                accentColor = R.color.programAccent
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentCustomListBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            customList.list.bottomPadding = requireContext().dp(8)
            customList.emptyTextResource = R.string.program_empty_error
            customList.refreshLayout.isEnabled = false

            viewModel.screenStateStream.observe(viewLifecycleOwner, Observer { state ->
                val overview = (state as? State.Loaded)?.data?.overview
                val programDataAvailable = overview?.isNotEmpty() == true

                customList.contentVisible = programDataAvailable
                customList.progressVisible = state is State.Loading
                customList.emptyTextVisible = state is State.Loaded && !programDataAvailable

                if (state is State.Loaded && overview != null && overview.isNotEmpty()) {
                    controller = ProgramController(
                        applicationContext = requireContext().applicationContext,
                        overview = state.data.overview,
                        deadlineInDays = state.data.programDeadline,
                        onProgramSelected = { program -> onProgramSelected(program.id, state.data) },
                        onClosedProgramSelected = { onClosedProgramSelected() },
                        onOutputTestSelected = { onOutputTestSelected() }
                    ).also {
                        it.requestModelBuild()
                        customList.list.setController(it)
                    }
                }
            })
        }
    }

    private fun onProgramSelected(programId: String, screenState: ProgramViewModel.ScreenState) {
        when (programId) {
            ProgramId.PROGRAM_FIRST_ID.txtId -> onFirstProgramSelected(screenState.programFirstResults)
            ProgramId.PROGRAM_SECOND_ID.txtId -> onSecondProgramSelected(screenState.programSecondResults, screenState.overview)
            ProgramId.PROGRAM_THIRD_ID.txtId -> onThirdProgramSelected(screenState.programThirdResults, screenState.overview)
            ProgramId.PROGRAM_FOURTH_ID.txtId -> onFourthProgramSelected(screenState.programFourthResults, screenState.overview)
        }
    }

    private fun onClosedProgramSelected() {
        ProgramClosedDialogFragment().show(childFragmentManager, TAG_PROGRAM_CLOSED_DIALOG)
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

    private fun onSecondProgramSelected(results: ProgramSecondResults, programs: List<ProgramOverview>) {
        val navController = findNavController()
        val programFirstOverview = programs.first { it.id == ProgramId.PROGRAM_FIRST_ID.txtId }

        if (programFirstOverview.evaluated) {
            if (results.progress < 1) {
                navController.navigate(R.id.action_fragment_program_overview_to_fragment_program_2_intro_1)
            } else {
                navController.navigate(R.id.action_fragment_program_overview_to_fragment_program_2_1)
            }
        } else {
            navController.navigate(
                R.id.action_fragment_program_overview_to_fragment_evaluation,
                ProgramEvaluationFragment.arguments(ProgramId.PROGRAM_FIRST_ID)
            )
        }
    }

    private fun onThirdProgramSelected(results: ProgramThirdResults, programs: List<ProgramOverview>) {
        val navController = findNavController()
        val programSecondOverview = programs.first { it.id == ProgramId.PROGRAM_SECOND_ID.txtId }

        if (programSecondOverview.evaluated) {
            if (results.programCompleted == null) {
                if (results.progress < 1) {
                    navController.navigate(R.id.action_fragment_program_overview_to_fragment_program_3_intro)
                } else {
                    // Build navigation backstack
                    navController.navigate(R.id.action_fragment_program_overview_to_fragment_program_3_1)
                    if (results.progress >= 2) navController.navigate(R.id.action_fragment_program_3_1_to_fragment_program_3_2)
                    if (results.progress >= 3) navController.navigate(R.id.action_fragment_program_3_2_to_fragment_program_3_3)
                    if (results.progress >= 4) navController.navigate(R.id.action_fragment_program_3_3_to_fragment_program_3_4)
                    if (results.progress >= 5) navController.navigate(R.id.action_fragment_program_3_4_to_fragment_program_3_5)
                    if (results.progress >= 6) navController.navigate(R.id.action_fragment_program_3_5_to_fragment_program_3_6)
                    if (results.progress >= 7) navController.navigate(R.id.action_fragment_program_3_6_to_fragment_program_3_7)
                    if (results.progress >= 8) navController.navigate(R.id.action_fragment_program_3_7_to_fragment_program_3_8)
                    if (results.progress >= 9) navController.navigate(R.id.action_fragment_program_3_8_to_fragment_program_3_9)
                    if (results.progress >= 10) navController.navigate(R.id.action_fragment_program_3_9_to_fragment_program_3_10)
                }
            } else {
                navController.navigate(R.id.action_fragment_program_overview_to_fragment_program_3_summary)
            }
        } else {
            navController.navigate(
                R.id.action_fragment_program_overview_to_fragment_evaluation,
                ProgramEvaluationFragment.arguments(ProgramId.PROGRAM_SECOND_ID)
            )
        }
    }

    private fun onFourthProgramSelected(results: ProgramFourthResults, programs: List<ProgramOverview>) {
        val navController = findNavController()
        val programThirdOverview = programs.first { it.id == ProgramId.PROGRAM_THIRD_ID.txtId }

        if (programThirdOverview.evaluated) {
            if (results.programCompleted == null) {
                // Build navigation backstack
                navController.navigate(R.id.action_fragment_program_overview_to_fragment_program_4_1)
                if (results.progress >= 2) navController.navigate(R.id.action_fragment_program_4_1_to_fragment_program_4_2)
                if (results.progress >= 3) navController.navigate(R.id.action_fragment_program_4_2_to_fragment_program_4_question_intro)
                if (results.progress >= 4) navController.navigate(R.id.action_fragment_program_4_question_intro_to_fragment_program_4_question)
            } else {
                navController.navigate(R.id.action_fragment_program_overview_to_fragment_program_4_summary)
            }
        } else {
            navController.navigate(
                R.id.action_fragment_program_overview_to_fragment_evaluation,
                ProgramEvaluationFragment.arguments(ProgramId.PROGRAM_THIRD_ID)
            )
        }
    }

    // TODO: add final retest ("fifth" program) with evaluation of program 4
    private fun onOutputTestSelected() {
        findNavController().navigate(R.id.action_fragment_program_overview_to_fragment_output_test_intro)
    }
}
