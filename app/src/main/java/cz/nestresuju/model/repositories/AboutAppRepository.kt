package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.AboutAppConverter
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.database.about.DbResearchSubsection
import cz.nestresuju.model.entities.domain.domain.ContactsCategory
import cz.nestresuju.model.entities.domain.domain.ResearchInfo
import cz.nestresuju.networking.ApiDefinition

/**
 * Repository providing all data related to "About app" section.
 */
interface AboutAppRepository {

    suspend fun fetchContacts()

    suspend fun getContacts(): List<ContactsCategory>

    suspend fun fetchResearchInfo()

    suspend fun getResearchInfo(): ResearchInfo
}

class AboutAppRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val database: AppDatabase,
    private val entityConverter: AboutAppConverter
) : AboutAppRepository {

    override suspend fun fetchContacts() {
        val apiContacts = apiDefinition.getContacts()

        database.aboutAppDao().updateContacts(apiContacts.categories.mapIndexed { index, apiCategory ->
            entityConverter.apiContactsCategoryToDb(
                apiContactsCategory = apiCategory,
                order = index
            )
        })
    }

    override suspend fun getContacts() =
        database.aboutAppDao().getContacts().map { dbCategory -> entityConverter.dbContactsCategoryToDomain(dbCategory) }

    override suspend fun fetchResearchInfo() {
        val apiResearch = apiDefinition.getResearchInfo()
        val textSubsection = DbResearchSubsection(order = 0, name = null, text = apiResearch.text)

        database.aboutAppDao().updateResearchSubsection(apiResearch.subsections.mapIndexed { index, apiSubsection ->
            entityConverter.apiResearchSubsectionToDb(apiSubsection, index + 1)
        } + textSubsection)
    }

    override suspend fun getResearchInfo(): ResearchInfo {
        val dbSubsections = database.aboutAppDao().getResearchSubsections()
        val introText = dbSubsections.find { it.name == null }?.text ?: ""
        val subsections = dbSubsections
            .filter { it.name != null }
            .map { dbSubsection -> entityConverter.dbResearchSubsectionsToDomain(dbSubsection) }

        return ResearchInfo(
            text = introText,
            subsections = subsections
        )
    }
}