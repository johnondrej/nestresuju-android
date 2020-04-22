package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.LibraryConverter
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.api.library.ApiLibrarySection
import cz.nestresuju.model.entities.domain.library.LibrarySection
import cz.nestresuju.networking.ApiDefinition

/**
 * Repository providing all data related to Library section.
 */
interface LibraryRepository {

    suspend fun fetchLibraryContent()

    suspend fun getLibraryContent(): LibrarySection
}

class LibraryRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val database: AppDatabase,
    private val entityConverter: LibraryConverter
) : LibraryRepository {

    override suspend fun fetchLibraryContent() {
        // TODO: remove testing data when API is ready
        // val libraryContent = apiDefinition.getLibraryContent()
        val libraryContent = testingLibraryContent()

        database.libraryDao().updateSections(entityConverter.apiLibrarySectionToDb(libraryContent, root = true))
    }

    override suspend fun getLibraryContent() = entityConverter.dbLibrarySectionsToDomain(database.libraryDao().getSections())

    private fun testingLibraryContent() = ApiLibrarySection(
        id = 1,
        name = "Stres",
        text = "Info o stresu",
        sections = emptyList()
    )
}