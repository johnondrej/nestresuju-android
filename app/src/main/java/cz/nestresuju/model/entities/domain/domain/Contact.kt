package cz.nestresuju.model.entities.domain.domain

/**
 * Contact info related to one person.
 */
data class Contact(
    val name: String,
    val image: String?,
    val description: String?,
    val email: String?,
    val phone: String?
)