package cz.nestresuju.screens.about.epoxy

import com.airbnb.epoxy.EpoxyController
import cz.nestresuju.R

/**
 * Epoxy controller for navigation between "About app" sections.
 */
class AboutAppController(
    private val onContactsClicked: () -> Unit
) : EpoxyController() {

    override fun buildModels() {
        aboutAppSection {
            id("contacts")
            title(R.string.about_contacts_title)
            icon(R.drawable.ic_contacts)
            onSectionClicked(onContactsClicked)
        }
    }
}