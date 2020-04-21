package cz.nestresuju.model.entities.database.about

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Database class representing contact category with all belonging contacts.
 */
data class DbContactCategoryWithContacts(
    @Embedded val category: DbContactsCategory,
    @Relation(
        parentColumn = "name",
        entityColumn = "category_name"
    )
    val contacts: List<DbContact>
)