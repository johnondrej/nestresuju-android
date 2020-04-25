package cz.nestresuju.screens.program.overview.epoxy

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import cz.nestresuju.R
import cz.nestresuju.views.common.epoxy.navigationCard

/**
 * Epoxy controller for programs overview.
 */
class ProgramController(
    private val applicationContext: Context,
    private val onFirstProgramSelected: () -> Unit,
    private val onSecondProgramSelected: () -> Unit,
    private val onThirdProgramSelected: () -> Unit,
    private val onFourthProgramSelected: () -> Unit
) : EpoxyController() {

    override fun buildModels() {
        // TODO: pass real states of programs when implemented

        navigationCard {
            id("program-1")
            title("Stanovování cílů")
            stateText(applicationContext.getString(R.string.program_state_open))
            onItemClicked(onFirstProgramSelected)
        }

        navigationCard {
            id("program-2")
            title("Relaxace")
            stateText(applicationContext.getString(R.string.program_state_open))
            onItemClicked(onSecondProgramSelected)
        }

        navigationCard {
            id("program-3")
            title("Řízení času")
            stateText(applicationContext.getString(R.string.program_state_open))
            onItemClicked(onThirdProgramSelected)
        }

        navigationCard {
            id("program-4")
            title("Hledání smyslu a pozitiv")
            stateText(applicationContext.getString(R.string.program_state_open))
            onItemClicked(onFourthProgramSelected)
        }
    }
}