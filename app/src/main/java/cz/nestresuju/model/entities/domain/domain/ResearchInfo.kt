package cz.nestresuju.model.entities.domain.domain

/**
 * Research info with subsections.
 */
data class ResearchInfo(
    val text: String,
    val subsections: List<ResearchSubsection>
)