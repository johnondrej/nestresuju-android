package cz.nestresuju.screens.about

import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.common.StateLiveData
import cz.nestresuju.model.entities.domain.domain.ContactsCategory
import cz.nestresuju.model.repositories.AboutAppRepository
import cz.nestresuju.screens.base.BaseViewModel

/**
 * ViewModel for screen with contacts list.
 */
class ContactsViewModel(
    private val aboutAppRepository: AboutAppRepository
) : BaseViewModel() {

    val contactsStream = StateLiveData<List<ContactsCategory>>()

    init {
        fetchContacts()
    }

    fun fetchContacts() {
        contactsStream.loading()
        viewModelScope.launchWithErrorHandling {
            try {
                aboutAppRepository.fetchContacts()
            } catch (e: Exception) {
                // Rethrow exception to allow further error handling, but still show contacts from DB
                contactsStream.loaded(aboutAppRepository.getContacts())
                throw e
            }
            contactsStream.loaded(aboutAppRepository.getContacts())
        }
    }
}