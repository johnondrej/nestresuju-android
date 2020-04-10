package cz.nestresuju.screens.program.second

import androidx.navigation.fragment.findNavController
import cz.nestresuju.R

/**
 * Fragment with sixth part of second program.
 */
class ProgramSecondPhase6Fragment : ProgramSecondBaseInstructionsFragment() {

    override val instructionsRes = R.string.program_2_instructions_6
    override val phaseProgress = 6

    override fun onContinueClicked() {
        findNavController().navigate(R.id.action_fragment_program_2_6_to_fragment_program_2_7)
    }
}