package cz.nestresuju.screens.about.epoxy

import com.airbnb.epoxy.EpoxyController
import cz.nestresuju.common.extensions.controllerProperty
import cz.nestresuju.model.entities.domain.domain.ResearchInfo

/**
 * Epoxy controller for displaying research info.
 */
class ResearchController : EpoxyController() {

    var researchInfo: ResearchInfo? by controllerProperty(null)

    override fun buildModels() {
        researchInfo?.let { info ->
            researchText {
                id("intro")
                researchText(info.text)
            }

            info.subsections.forEach { subsection ->
                researchSubsection {
                    id("subsection-${subsection.name}")
                    headlineText(subsection.name)
                    contentText(subsection.text)
                }
            }
        }
    }
}