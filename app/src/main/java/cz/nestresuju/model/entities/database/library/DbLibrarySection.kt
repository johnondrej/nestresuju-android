package cz.nestresuju.model.entities.database.library

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

/**
 * Database class representing one section in the library.
 */
@Entity(
    tableName = "LibrarySections",
    foreignKeys = [ForeignKey(
        entity = DbLibrarySection::class,
        parentColumns = ["id"],
        childColumns = ["parent_section_id"],
        onDelete = CASCADE
    )]
)
data class DbLibrarySection(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "parent_section_id") val parentSectionId: Long?,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "root") val root: Boolean = false
)