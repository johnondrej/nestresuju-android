package cz.nestresuju.screens.program.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentProgram2Intro2Binding
import cz.nestresuju.screens.base.BaseFragment

/**
 * Screen with first part of intro to second program.
 */
class ProgramSecondIntro2Fragment : BaseFragment<FragmentProgram2Intro2Binding>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram2Intro2Binding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            btnContinue.setOnClickListener {
                findNavController().navigate(R.id.action_fragment_program_2_intro_2_to_fragment_program_2_1)
            }

            btnCancel.setOnClickListener {
                findNavController().popBackStack(R.id.navigation_program, false)
            }
        }
    }
}