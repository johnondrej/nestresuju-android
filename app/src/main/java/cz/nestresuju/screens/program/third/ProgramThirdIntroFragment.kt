package cz.nestresuju.screens.program.third

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cz.nestresuju.R
import cz.nestresuju.databinding.FragmentProgram3IntroBinding
import cz.nestresuju.screens.base.BaseFragment

/**
 * Fragment with intro to third program.
 */
class ProgramThirdIntroFragment : BaseFragment<FragmentProgram3IntroBinding>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentProgram3IntroBinding.inflate(inflater, container, false).also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_program_3_intro_to_fragment_program_3_1)
        }
    }
}