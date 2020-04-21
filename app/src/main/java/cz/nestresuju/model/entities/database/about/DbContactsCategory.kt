package cz.nestresuju.model.entities.database.about

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database entity representing contacts category.
 */
@Entity(tableName = "ContactsCategory")
data class DbContactsCategory(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "order") val order: Int
)