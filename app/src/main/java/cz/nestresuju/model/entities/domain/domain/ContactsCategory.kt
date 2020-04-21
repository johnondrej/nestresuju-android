package cz.nestresuju.model.entities.domain.domain

/**
 * Category of contacts.
 */
data class ContactsCategory(
    val name: String,
    val contacts: List<Contact>
)