package cz.nestresuju.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cz.nestresuju.model.entities.database.diary.DbSynchronizerDiaryChange

/**
 * Database DAO for storing synchronization data.
 */
@Dao
abstract class SynchronizerDao {

    @Query("SELECT * FROM SynchronizationDiaryChanges")
    abstract suspend fun getAllDiaryChanges(): List<DbSynchronizerDiaryChange>

    @Query("SELECT * FROM SynchronizationDiaryChanges WHERE id = :diaryChangeId")
    abstract suspend fun findDiaryChange(diaryChangeId: Long): DbSynchronizerDiaryChange?

    @Insert
    abstract suspend fun addDiaryChange(diaryChange: DbSynchronizerDiaryChange)

    @Update
    abstract suspend fun updateDiaryChange(diaryChange: DbSynchronizerDiaryChange)

    @Query("DELETE FROM SynchronizationDiaryChanges WHERE id = :diaryChangeId")
    abstract suspend fun deleteDiaryChange(diaryChangeId: Long)

    @Query("DELETE FROM SynchronizationDiaryChanges")
    abstract suspend fun deleteDiaryChanges()
}