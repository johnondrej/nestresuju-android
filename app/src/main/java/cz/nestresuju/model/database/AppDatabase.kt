package cz.nestresuju.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.nestresuju.model.database.converters.RoomTypeConverters
import cz.nestresuju.model.database.dao.*
import cz.nestresuju.model.entities.database.diary.DbDiaryEntry
import cz.nestresuju.model.entities.database.diary.DbStressQuestion
import cz.nestresuju.model.entities.database.diary.DbSynchronizerDiaryChange
import cz.nestresuju.model.entities.database.program.evaluation.DbProgramEvaluation
import cz.nestresuju.model.entities.database.program.first.DbProgramFirstResults
import cz.nestresuju.model.entities.database.program.second.DbProgramSecondResults

/**
 * Class representing app database.
 */
@Database(
    entities = [
        DbProgramFirstResults::class,
        DbProgramSecondResults::class,
        DbDiaryEntry::class,
        DbStressQuestion::class,
        DbSynchronizerDiaryChange::class,
        DbProgramEvaluation::class
    ],
    version = 1
)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        const val NAME = "nestresuju_db"
    }

    abstract fun programEvaluationDao(): ProgramEvaluationDao

    abstract fun programFirstDao(): ProgramFirstDao

    abstract fun programSecondDao(): ProgramSecondDao

    abstract fun diaryDao(): DiaryDao

    abstract fun synchronizerDao(): SynchronizerDao
}