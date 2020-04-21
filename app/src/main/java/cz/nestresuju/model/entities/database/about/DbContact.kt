package cz.nestresuju.model.entities.database.about

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Database entity representing contact info.
 */
@Entity(
    tableName = "Contacts",
    foreignKeys = [ForeignKey(
        entity = DbContactsCategory::class,
        parentColumns = ["name"],
        childColumns = ["category_name"]
    )]
)
data class DbContact(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "category_name") val categoryName: String,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "phone") val phone: String?
)