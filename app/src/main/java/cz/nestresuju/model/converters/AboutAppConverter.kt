package cz.nestresuju.model.converters

import cz.nestresuju.model.entities.api.about.ApiContactsCategory
import cz.nestresuju.model.entities.api.about.ApiResearchSubsection
import cz.nestresuju.model.entities.database.about.DbContact
import cz.nestresuju.model.entities.database.about.DbContactCategoryWithContacts
import cz.nestresuju.model.entities.database.about.DbContactsCategory
import cz.nestresuju.model.entities.database.about.DbResearchSubsection
import cz.nestresuju.model.entities.domain.domain.Contact
import cz.nestresuju.model.entities.domain.domain.ContactsCategory
import cz.nestresuju.model.entities.domain.domain.ResearchSubsection

/**
 * Converter for converting API & DB & domain entities for "About app" section.
 */
interface AboutAppConverter {

    fun dbContactToDomain(dbContact: DbContact): Contact

    fun dbContactsCategoryToDomain(dbContactsCategory: DbContactCategoryWithContacts): ContactsCategory

    fun apiContactsCategoryToDb(apiContactsCategory: ApiContactsCategory, order: Int): DbContactCategoryWithContacts

    fun dbResearchSubsectionsToDomain(dbResearchSubsection: DbResearchSubsection): ResearchSubsection

    fun apiResearchSubsectionToDb(apiResearchSubsection: ApiResearchSubsection, order: Int): DbResearchSubsection
}

class AboutAppConverterImpl : AboutAppConverter {

    override fun dbContactToDomain(dbContact: DbContact): Contact {
        return Contact(
            name = dbContact.name,
            image = dbContact.image,
            description = dbContact.description,
            email = dbContact.email,
            phone = dbContact.phone
        )
    }

    override fun dbContactsCategoryToDomain(dbContactsCategory: DbContactCategoryWithContacts): ContactsCategory {
        return ContactsCategory(
            name = dbContactsCategory.category.name,
            contacts = dbContactsCategory.contacts.map { dbContact -> dbContactToDomain(dbContact) }
        )
    }

    override fun apiContactsCategoryToDb(apiContactsCategory: ApiContactsCategory, order: Int): DbContactCategoryWithContacts {
        return DbContactCategoryWithContacts(
            category = DbContactsCategory(
                name = apiContactsCategory.name,
                order = order
            ), contacts = apiContactsCategory.members.mapIndexed { index, apiContact ->
                DbContact(
                    categoryName = apiContactsCategory.name,
                    order = index,
                    image = apiContact.image,
                    name = apiContact.heading,
                    description = apiContact.description,
                    email = apiContact.email,
                    phone = apiContact.phone
                )
            }
        )
    }

    override fun dbResearchSubsectionsToDomain(dbResearchSubsection: DbResearchSubsection): ResearchSubsection {
        return ResearchSubsection(
            name = dbResearchSubsection.name!!,
            text = dbResearchSubsection.text
        )
    }

    override fun apiResearchSubsectionToDb(apiResearchSubsection: ApiResearchSubsection, order: Int): DbResearchSubsection {
        return DbResearchSubsection(
            order = order,
            name = apiResearchSubsection.name,
            text = apiResearchSubsection.text
        )
    }
}