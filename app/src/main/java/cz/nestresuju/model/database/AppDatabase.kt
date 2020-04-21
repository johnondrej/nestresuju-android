package cz.nestresuju.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.nestresuju.model.database.converters.RoomTypeConverters
import cz.nestresuju.model.database.dao.*
import cz.nestresuju.model.entities.database.about.DbContact
import cz.nestresuju.model.entities.database.about.DbContactsCategory
import cz.nestresuju.model.entities.database.about.DbResearchSubsection
import cz.nestresuju.model.entities.database.diary.DbDiaryEntry
import cz.nestresuju.model.entities.database.diary.DbStressQuestion
import cz.nestresuju.model.entities.database.diary.DbSynchronizerDiaryChange
import cz.nestresuju.model.entities.database.program.evaluation.DbProgramEvaluation
import cz.nestresuju.model.entities.database.program.first.DbProgramFirstResults
import cz.nestresuju.model.entities.database.program.fourth.DbProgramFourthQuestion
import cz.nestresuju.model.entities.database.program.fourth.DbProgramFourthResults
import cz.nestresuju.model.entities.database.program.second.DbProgramSecondResults
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdActivity
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdHours
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdResults

/**
 * Class representing app database.
 */
@Database(
    entities = [
        DbProgramFirstResults::class,
        DbProgramSecondResults::class,
        DbProgramThirdResults::class,
        DbProgramThirdHours::class,
        DbProgramThirdActivity::class,
        DbProgramFourthResults::class,
        DbProgramFourthQuestion::class,
        DbDiaryEntry::class,
        DbStressQuestion::class,
        DbSynchronizerDiaryChange::class,
        DbProgramEvaluation::class,
        DbContact::class,
        DbContactsCategory::class,
        DbResearchSubsection::class
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

    abstract fun programThirdDao(): ProgramThirdDao

    abstract fun programFourthDao(): ProgramFourthDao

    abstract fun diaryDao(): DiaryDao

    abstract fun aboutAppDao(): AboutAppDao

    abstract fun synchronizerDao(): SynchronizerDao
}