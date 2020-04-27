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

    suspend fun getLibraryContent(): LibrarySection?
}

class LibraryRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val database: AppDatabase,
    private val entityConverter: LibraryConverter
) : LibraryRepository {

    override suspend fun fetchLibraryContent() {
        val libraryContent = apiDefinition.getLibraryContent()
        val rootSection = ApiLibrarySection(
            id = -1,
            name = null,
            text = libraryContent.libraryIntroduction,
            sections = libraryContent.sections
        )

        database.libraryDao().updateSections(
            entityConverter.apiLibrarySectionToDb(rootSection, root = true)
        )
    }

    override suspend fun getLibraryContent() = entityConverter.dbLibrarySectionsToDomain(database.libraryDao().getSections())

}