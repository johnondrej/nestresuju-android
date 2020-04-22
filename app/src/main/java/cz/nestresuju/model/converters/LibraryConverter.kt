package cz.nestresuju.model.converters

import cz.nestresuju.model.entities.api.library.ApiLibrarySection
import cz.nestresuju.model.entities.database.library.DbLibrarySection
import cz.nestresuju.model.entities.domain.library.LibrarySection

/**
 * Converter for library entities.
 */
interface LibraryConverter {

    fun dbLibrarySectionsToDomain(dbSections: List<DbLibrarySection>, rootSection: DbLibrarySection? = null): LibrarySection

    fun apiLibrarySectionToDb(apiSection: ApiLibrarySection, parentId: Long? = null, order: Int = 0, root: Boolean): List<DbLibrarySection>
}

class LibraryConverterImpl : LibraryConverter {

    override fun dbLibrarySectionsToDomain(dbSections: List<DbLibrarySection>, rootSection: DbLibrarySection?): LibrarySection {
        val root = rootSection ?: dbSections.find { it.root } ?: throw IllegalStateException("No root section found")
        val subsections = dbSections.filter { it.parentSectionId == root.id }

        return LibrarySection(
            id = root.id,
            name = root.name,
            text = root.text,
            subsections = subsections.map { subsection ->
                dbLibrarySectionsToDomain(
                    dbSections = dbSections,
                    rootSection = subsection
                )
            }
        )
    }

    override fun apiLibrarySectionToDb(apiSection: ApiLibrarySection, parentId: Long?, order: Int, root: Boolean): List<DbLibrarySection> {
        return listOf(
            DbLibrarySection(
                id = apiSection.id,
                name = apiSection.name,
                text = apiSection.text,
                parentSectionId = parentId,
                order = order,
                root = root
            )
        ) + apiSection.sections.mapIndexed { index, subsection ->
            apiLibrarySectionToDb(subsection, parentId = apiSection.id, order = index, root = false)
        }.flatten()
    }
}