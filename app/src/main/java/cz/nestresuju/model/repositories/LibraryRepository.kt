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
        // TODO: remove testing data when API is ready
        // val libraryContent = apiDefinition.getLibraryContent()
        val libraryContent = testingLibraryContent()

        database.libraryDao().updateSections(
            libraryContent?.let { entityConverter.apiLibrarySectionToDb(libraryContent, root = true) } ?: emptyList()
        )
    }

    override suspend fun getLibraryContent() = entityConverter.dbLibrarySectionsToDomain(database.libraryDao().getSections())

    private fun testingLibraryContent(): ApiLibrarySection? = ApiLibrarySection(
        id = 0,
        name = null,
        text = "Úvodní text knihovny",
        sections = listOf(
            ApiLibrarySection(
                id = 1,
                name = "Stres",
                text = null,
                sections = listOf(
                    ApiLibrarySection(
                        id = 2,
                        name = "Co je to stres?",
                        text = "Obsah subsekce",
                        sections = emptyList()
                    ),
                    ApiLibrarySection(
                        id = 3,
                        name = "Je to normální?",
                        text = "Obsah subsekce 2",
                        sections = listOf(
                            ApiLibrarySection(
                                id = 4,
                                name = "Třetí vnořená podsekce",
                                text = "Obsah třetí podsekce!",
                                sections = emptyList()
                            )
                        )
                    )
                )
            ),
            ApiLibrarySection(
                id = 5,
                name = "Řízení stresu",
                text = "Úvodní text k řízení stresu",
                sections = listOf(
                    ApiLibrarySection(
                        id = 6,
                        name = "Stress management",
                        text = "Něco o stress managementu",
                        sections = emptyList()
                    )
                )
            )
        )
    )

}