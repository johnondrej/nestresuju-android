package cz.nestresuju.screens.program.fourth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.visible
import cz.nestresuju.databinding.FragmentProgram4IntroQuestionsBinding
import cz.nestresuju.model.common.State
import cz.nestresuju.model.errors.InternetConnectionException
import cz.nestresuju.model.errors.handlers.InternetErrorsHandler
import cz.nestresuju.model.errors.handlers.UnknownErrorsHandler
import cz.nestresuju.screens.base.BaseArchFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment with instructions about filling in questionnaire from program 4.
 */
class ProgramFourthQuestionsIntroFragment : BaseArchFragment<FragmentProgram4IntroQuestionsBinding>() {

    override val viewModel by viewModel<ProgramFourthQuestionsIntroViewModel>()

    override val errorHandlers = arrayOf(
        InternetErrorsHandler(),
        UnknownErrorsHandler()
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram4IntroQuestionsBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            progress.progress = 3
            progress.max = ProgramFourthConstants.PHASES

            viewModel.loadingStateStream.observe(viewLifecycleOwner, Observer { state ->
                layoutContent.visible = state is State.Loaded
                layoutInternetError.visible = (state as? State.Error)?.error is InternetConnectionException
                loadingProgress.visible = state == State.Loading
            })

            btnContinue.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_program_4_question_intro_to_fragment_program_4_question)
            }

            btnTryAgain.setOnClickListener {
                viewModel.tryAgain()
            }
        }
    }
}