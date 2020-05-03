package cz.nestresuju.screens.program.overview.epoxy

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.formatDateTime
import cz.nestresuju.model.entities.domain.program.overview.ProgramOverview
import cz.nestresuju.screens.home.epoxy.homeProgramDeadline
import cz.nestresuju.views.common.epoxy.navigationCard

/**
 * Epoxy controller for programs overview.
 */
class ProgramController(
    private val applicationContext: Context,
    private val overview: List<ProgramOverview>,
    private val showOutputTest: Boolean,
    private val deadlineInDays: Int?,
    private val onProgramSelected: (ProgramOverview) -> Unit,
    private val onClosedProgramSelected: () -> Unit,
    private val onOutputTestSelected: () -> Unit
) : EpoxyController() {

    override fun buildModels() {
        overview
            .sortedBy { it.order }
            .forEach { program ->
                val programOpened = program.isOpened
                val stateText = when (programOpened) {
                    true -> when (program.completed) {
                        true -> applicationContext.getString(R.string.program_state_completed)
                        false -> applicationContext.getString(R.string.program_state_open)
                    }
                    false -> applicationContext.getString(R.string.program_state_closed)
                }
                val stateDescriptionText = when (programOpened) {
                    true -> null
                    false -> when (program.startDate) {
                        null -> applicationContext.getString(R.string.program_state_closed_complete_previous)
                        else -> applicationContext.getString(
                            R.string.program_state_opens_at,
                            program.startDate.toLocalDateTime().formatDateTime()
                        )
                    }
                }

                navigationCard {
                    id(program.id)
                    title(program.name)
                    stateText(stateText)
                    stateDescriptionText(stateDescriptionText ?: "")
                    active(programOpened)
                    if (programOpened) {
                        onItemClicked { onProgramSelected(program) }
                    } else {
                        onItemClicked { onClosedProgramSelected() }
                    }
                }
            }

        if (showOutputTest) {
            navigationCard {
                id("output-test")
                title(applicationContext.getString(R.string.output_test_title))
                stateText(applicationContext.getString(R.string.program_state_open))
                onItemClicked(onOutputTestSelected)
            }
        }

        deadlineInDays?.let { deadline ->
            homeProgramDeadline {
                id("item-deadline")
                daysUntilDeadline(deadline)
            }
        }
    }
}