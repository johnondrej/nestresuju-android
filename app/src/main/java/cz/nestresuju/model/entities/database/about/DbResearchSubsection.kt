package cz.nestresuju.model.entities.database.about

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database entity representing subsection with info about research.
 */
@Entity
data class DbResearchSubsection(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "text") val text: String
)