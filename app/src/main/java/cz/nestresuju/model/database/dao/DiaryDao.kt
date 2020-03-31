package cz.nestresuju.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import cz.nestresuju.model.entities.database.diary.DbDiaryEntry
import cz.nestresuju.model.entities.database.diary.DbDiaryEntryWithQuestion
import cz.nestresuju.model.entities.database.diary.DbStressQuestion
import kotlinx.coroutines.flow.Flow

/**
 * Database DAO for storing diary entries.
 */
@Dao
abstract class DiaryDao {

    @Transaction
    @Query("SELECT * FROM DiaryEntries ORDER BY date_created DESC")
    abstract fun observeEntries(): Flow<List<DbDiaryEntryWithQuestion>>

    @Insert
    abstract suspend fun addEntry(diaryEntry: DbDiaryEntry): Long

    @Insert
    abstract suspend fun addEntries(diaryEntries: List<DbDiaryEntry>)

    @Query("UPDATE DiaryEntries SET text = :text WHERE id = :entryId")
    abstract suspend fun editEntry(entryId: Long, text: String)

    @Query("DELETE FROM DiaryEntries WHERE id = :entryId")
    abstract suspend fun deleteEntry(entryId: Long)

    @Query("DELETE FROM DiaryEntries")
    abstract suspend fun deleteEntries()

    @Query("SELECT * FROM StressQuestions")
    abstract suspend fun getStressQuestions(): List<DbStressQuestion>

    @Insert
    abstract suspend fun addQuestions(stressQuestions: List<DbStressQuestion>)

    @Query("DELETE FROM StressQuestions")
    abstract suspend fun deleteQuestions()

    @Transaction
    open suspend fun updateDiary(diaryEntries: List<DbDiaryEntry>, stressQuestions: List<DbStressQuestion>) {
        deleteEntries()
        deleteQuestions()
        addQuestions(stressQuestions)
        addEntries(diaryEntries)
    }
}