package cz.nestresuju.model.database.dao

import androidx.room.*
import cz.nestresuju.model.entities.database.diary.DbDiaryEntry
import cz.nestresuju.model.entities.database.diary.DbDiaryEntryWithQuestion
import cz.nestresuju.model.entities.database.diary.DbStressQuestion

/**
 * Database DAO for storing diary entries.
 */
@Dao
abstract class DiaryDao {

    @Transaction
    @Query("SELECT * FROM DiaryEntries")
    abstract suspend fun getEntries(): List<DbDiaryEntryWithQuestion>

    @Insert
    abstract suspend fun addEntry(diaryEntry: DbDiaryEntry)

    @Insert
    abstract suspend fun addEntries(diaryEntries: List<DbDiaryEntry>)

    @Delete
    abstract suspend fun deleteEntry(diaryEntry: DbDiaryEntry)

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