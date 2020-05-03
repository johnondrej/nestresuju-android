package cz.nestresuju.model.database

import android.content.Context
import androidx.annotation.StringRes
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.nestresuju.R
import cz.nestresuju.model.database.converters.RoomTypeConverters
import cz.nestresuju.model.database.dao.*
import cz.nestresuju.model.entities.database.about.DbContact
import cz.nestresuju.model.entities.database.about.DbContactsCategory
import cz.nestresuju.model.entities.database.about.DbResearchSubsection
import cz.nestresuju.model.entities.database.diary.DbDiaryEntry
import cz.nestresuju.model.entities.database.diary.DbStressQuestion
import cz.nestresuju.model.entities.database.diary.DbSynchronizerDiaryChange
import cz.nestresuju.model.entities.database.library.DbLibrarySection
import cz.nestresuju.model.entities.database.program.evaluation.DbProgramEvaluation
import cz.nestresuju.model.entities.database.program.first.DbProgramFirstResults
import cz.nestresuju.model.entities.database.program.fourth.DbProgramFourthQuestion
import cz.nestresuju.model.entities.database.program.fourth.DbProgramFourthResults
import cz.nestresuju.model.entities.database.program.overview.DbProgramOverview
import cz.nestresuju.model.entities.database.program.second.DbProgramSecondResults
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdActivity
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdHours
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdResults

/**
 * Class representing app database.
 */
@Database(
    entities = [
        DbProgramOverview::class,
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
        DbLibrarySection::class,
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

        suspend fun initDefaultValues(applicationContext: Context, database: AppDatabase) {
            database.programFirstDao().updateResults(DbProgramFirstResults())
            database.programSecondDao().updateResults(DbProgramSecondResults())
            database.programThirdDao().updateResults(DbProgramThirdResults())
            database.programFourthDao().updateResults(DbProgramFourthResults())
            addDefaultActivities(applicationContext, database)
        }

        private suspend fun addDefaultActivities(applicationContext: Context, database: AppDatabase) {
            val defaultActivities = listOf(
                defaultActivity(applicationContext, R.string.program_3_activities_hygiene),
                defaultActivity(applicationContext, R.string.program_3_activities_study),
                defaultActivity(applicationContext, R.string.program_3_activities_sport),
                defaultActivity(applicationContext, R.string.program_3_activities_work),
                defaultActivity(applicationContext, R.string.program_3_activities_household_care),
                defaultActivity(applicationContext, R.string.program_3_activities_culture),
                defaultActivity(applicationContext, R.string.program_3_activities_food),
                defaultActivity(applicationContext, R.string.program_3_activities_family_time),
                defaultActivity(applicationContext, R.string.program_3_activities_friends_time),
                defaultActivity(applicationContext, R.string.program_3_activities_partner_time)
            )

            database.programThirdDao().updateActivities(defaultActivities)
        }

        private fun defaultActivity(applicationContext: Context, @StringRes nameRes: Int) = DbProgramThirdActivity(
            name = applicationContext.getString(nameRes),
            hours = 0,
            minutes = 0,
            userDefined = false
        )
    }

    abstract fun programOverviewDao(): ProgramOverviewDao

    abstract fun programEvaluationDao(): ProgramEvaluationDao

    abstract fun programFirstDao(): ProgramFirstDao

    abstract fun programSecondDao(): ProgramSecondDao

    abstract fun programThirdDao(): ProgramThirdDao

    abstract fun programFourthDao(): ProgramFourthDao

    abstract fun diaryDao(): DiaryDao

    abstract fun libraryDao(): LibraryDao

    abstract fun aboutAppDao(): AboutAppDao

    abstract fun synchronizerDao(): SynchronizerDao
}