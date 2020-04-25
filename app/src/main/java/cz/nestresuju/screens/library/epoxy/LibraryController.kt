package cz.nestresuju.screens.library.epoxy

import com.airbnb.epoxy.EpoxyController
import cz.nestresuju.common.extensions.controllerProperty
import cz.nestresuju.model.entities.domain.library.LibrarySection

/**
 * Epoxy controller that shows library content.
 */
class LibraryController(
    private val onSubsectionClicked: (LibrarySection) -> Unit
) : EpoxyController() {

    var librarySection: LibrarySection? by controllerProperty(null)

    override fun buildModels() {
        librarySection?.let { section ->
            section.name?.let { sectionTitle ->
                librarySectionTitle {
                    id("title")
                    title(sectionTitle)
                }
            }

            section.text?.let { sectionText ->
                libraryContent {
                    id("content")
                    content(sectionText)
                }
            }

            section.subsections.forEach { subsection ->
                librarySubsection {
                    id("subsection-${subsection.id}")
                    subsection(subsection)
                    onSubsectionClicked(onSubsectionClicked)
                }
            }
        }
    }
}