package cz.nestresuju.screens.library

import androidx.lifecycle.viewModelScope
import cz.nestresuju.model.common.StateLiveData
import cz.nestresuju.model.entities.domain.library.LibrarySection
import cz.nestresuju.model.repositories.LibraryRepository
import cz.nestresuju.screens.base.BaseViewModel

class LibraryViewModel(
    private val libraryRepository: LibraryRepository
) : BaseViewModel() {

    private val libraryContent = mutableListOf<LibrarySection>()

    var sectionStream = StateLiveData<LibrarySection>()

    init {
        fetchLibraryContent()
    }

    fun fetchLibraryContent() {
        sectionStream.loading()
        viewModelScope.launchWithErrorHandling {
            try {
                libraryRepository.fetchLibraryContent()
            } catch (e: Exception) {
                // Rethrow exception to allow further error handling, but still show contacts from DB
                loadLibraryContent()
                throw e
            }
            loadLibraryContent()
        }
    }

    private suspend fun loadLibraryContent() {
        val librarySection = libraryRepository.getLibraryContent()

        libraryContent.clear()
        if (librarySection != null) {
            libraryContent += librarySection
            sectionStream.loaded(librarySection)
        } else {
            sectionStream.empty()
        }
    }

    fun onSubsectionSelected(subsection: LibrarySection) {
        libraryContent += subsection
        sectionStream.loaded(subsection)
    }

    fun onParentSectionSelected(): Boolean {
        if (libraryContent.size >= 2) {
            libraryContent.removeAt(libraryContent.lastIndex)
            sectionStream.loaded(libraryContent.last())
            return true
        }
        return false
    }
}