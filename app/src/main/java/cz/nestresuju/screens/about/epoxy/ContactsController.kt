package cz.nestresuju.screens.about.epoxy

import com.airbnb.epoxy.EpoxyController
import cz.nestresuju.common.extensions.adapterProperty
import cz.nestresuju.model.entities.domain.domain.ContactsCategory

/**
 * Epoxy controller for displaying list of contacts.
 */
class ContactsController(
    private val onEmailClicked: (String) -> Unit,
    private val onPhoneClicked: (String) -> Unit,
    private val onMessageClicked: (String) -> Unit
) : EpoxyController() {

    var contacts by adapterProperty(emptyList<ContactsCategory>())

    override fun buildModels() {
        contacts.forEach { category ->
            contactTitle {
                id("category-${category.name}")
                categoryTitle(category.name)
            }

            category.contacts.forEach { contact ->
                contact {
                    id("contact-${contact.name}")
                    name(contact.name)
                    photoUrl(contact.image)
                    description(contact.description)
                    email(contact.email)
                    phone(contact.phone)
                    onEmailClicked(onEmailClicked)
                    onPhoneClicked(onPhoneClicked)
                    onMessageClicked(onMessageClicked)
                }
            }
        }
    }
}