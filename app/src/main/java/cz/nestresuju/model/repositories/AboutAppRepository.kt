package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.AboutAppConverter
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.api.about.ApiContact
import cz.nestresuju.model.entities.api.about.ApiContactsCategory
import cz.nestresuju.model.entities.api.about.ContactsResponse
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
        // TODO: remove testing data when API is ready
        // val apiContacts = apiDefinition.getContacts()
        val apiContacts = testingApiContacts()

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
        val subsections = dbSubsections.map { dbSubsection -> entityConverter.dbResearchSubsectionsToDomain(dbSubsection) }

        return ResearchInfo(
            text = introText,
            subsections = subsections
        )
    }

    private fun testingApiContacts() = ContactsResponse(
        categories = listOf(
            ApiContactsCategory(
                name = "Manažerka stresu",
                items = listOf(
                    ApiContact(
                        image = "https://i.pinimg.com/originals/19/35/86/193586ea523e9527f4e89a8cffd8f926.jpg",
                        heading = "Jana Nováková",
                        description = "Doplňující info",
                        email = "pomoc@nestresuju.cz",
                        phone = "+420 777 123 123"
                    )
                )
            ),
            ApiContactsCategory(
                name = "IT ve stresu",
                items = listOf(
                    ApiContact(
                        image = "https://silnicnimotorky.cz/wp-content/uploads/2016/07/hd-low-rider-s_1102-1280x853.jpg",
                        heading = "Jan Novák",
                        description = null,
                        email = "jenda@nestresuju.cz",
                        phone = null
                    ),
                    ApiContact(
                        image = "https://simpsonovi.eu/wp-content/uploads/2019/02/homer_simpson_citaty_hlasky_simpsonovi.eu_.jpg",
                        heading = "Homer Simpson",
                        description = null,
                        email = null,
                        phone = "+420 777 123 456"
                    )
                )
            )
        )
    )
}