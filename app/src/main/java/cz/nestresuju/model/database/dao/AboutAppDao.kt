package cz.nestresuju.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import cz.nestresuju.model.entities.database.about.DbContact
import cz.nestresuju.model.entities.database.about.DbContactCategoryWithContacts
import cz.nestresuju.model.entities.database.about.DbContactsCategory
import cz.nestresuju.model.entities.database.about.DbResearchSubsection

/**
 * Database DAO for storing "about app" info.
 */
@Dao
abstract class AboutAppDao {

    @Transaction
    @Query("SELECT * FROM ContactsCategory ORDER BY `order`")
    abstract suspend fun getContacts(): List<DbContactCategoryWithContacts>

    @Insert
    abstract suspend fun addContactCategories(contactCategories: List<DbContactsCategory>)

    @Insert
    abstract suspend fun addContacts(contacts: List<DbContact>)

    @Transaction
    open suspend fun updateContacts(contacts: List<DbContactCategoryWithContacts>) {
        deleteContacts()
        deleteContactCategories()
        addContactCategories(contacts.map { it.category })
        addContacts(contacts.map { it.contacts }.flatten())
    }

    @Query("DELETE FROM Contacts")
    abstract suspend fun deleteContacts()

    @Query("DELETE FROM ContactsCategory")
    abstract suspend fun deleteContactCategories()

    @Query("SELECT * FROM DbResearchSubsection ORDER BY `order`")
    abstract suspend fun getResearchSubsections(): List<DbResearchSubsection>

    @Insert
    abstract suspend fun addResearchSubsections(subsections: List<DbResearchSubsection>)

    @Transaction
    open suspend fun updateResearchSubsection(subsections: List<DbResearchSubsection>) {
        deleteResearchSubsections()
        addResearchSubsections(subsections)
    }

    @Query("DELETE FROM DbResearchSubsection")
    abstract suspend fun deleteResearchSubsections()
}