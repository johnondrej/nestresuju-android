package cz.nestresuju.screens.about.epoxy

import com.airbnb.epoxy.EpoxyController
import cz.nestresuju.R

/**
 * Epoxy controller for navigation between "About app" sections.
 */
class AboutAppController(
    private val onContactsClicked: () -> Unit,
    private val onResearchClicked: () -> Unit,
    private val onFeedbackClicked: () -> Unit
) : EpoxyController() {

    override fun buildModels() {
        aboutAppSection {
            id("contacts")
            title(R.string.about_contacts_title)
            icon(R.drawable.ic_contacts)
            onSectionClicked(onContactsClicked)
        }

        aboutAppSection {
            id("research")
            title(R.string.about_research_title)
            icon(R.drawable.ic_research)
            onSectionClicked(onResearchClicked)
        }

        aboutAppSection {
            id("feedback")
            title(R.string.about_feedback_title)
            icon(R.drawable.ic_feedback)
            onSectionClicked(onFeedbackClicked)
        }
    }
}