package cz.nestresuju.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import cz.nestresuju.model.entities.database.library.DbLibrarySection

/**
 * Database DAO for storing library content.
 */
@Dao
abstract class LibraryDao {

    @Transaction
    @Query("SELECT * FROM LibrarySections")
    abstract suspend fun getSections(): List<DbLibrarySection>

    @Insert
    @Transaction
    abstract suspend fun addSections(sections: List<DbLibrarySection>)

    @Transaction
    open suspend fun updateSections(sections: List<DbLibrarySection>) {
        deleteSections()
        addSections(sections)
    }

    @Query("DELETE FROM LibrarySections WHERE root = 1")
    abstract suspend fun deleteSections()
}