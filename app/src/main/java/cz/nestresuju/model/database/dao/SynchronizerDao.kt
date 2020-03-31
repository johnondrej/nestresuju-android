package cz.nestresuju.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import cz.nestresuju.model.entities.database.diary.SynchronizerDbDiaryChange

/**
 * Database DAO for storing synchronization data.
 */
@Dao
abstract class SynchronizerDao {

    @Query("SELECT * FROM SynchronizationDiary")
    abstract suspend fun getAllDiaryChanges(): List<SynchronizerDbDiaryChange>

    @Query("SELECT * FROM SynchronizationDiary WHERE id = :diaryChangeId")
    abstract suspend fun findDiaryChange(diaryChangeId: Long): SynchronizerDbDiaryChange?

    @Insert
    abstract suspend fun addDiaryChange(diaryChange: SynchronizerDbDiaryChange)

    @Update
    abstract suspend fun updateDiaryChange(diaryChange: SynchronizerDbDiaryChange)

    @Query("DELETE FROM SynchronizationDiary WHERE id = :diaryChangeId")
    abstract suspend fun deleteDiaryChange(diaryChangeId: Long)

    @Query("DELETE FROM SynchronizationDiary")
    abstract suspend fun deleteDiaryChanges()
}