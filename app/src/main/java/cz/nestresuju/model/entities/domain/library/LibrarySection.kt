package cz.nestresuju.model.entities.domain.library

/**
 * Entity representing library section.
 */
data class LibrarySection(
    val id: Long,
    val name: String?,
    val text: String?,
    val subsections: List<LibrarySection>
)