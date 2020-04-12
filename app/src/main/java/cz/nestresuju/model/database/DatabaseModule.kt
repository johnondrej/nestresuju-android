package cz.nestresuju.model.database

import android.content.Context
import androidx.annotation.StringRes
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.nestresuju.R
import cz.nestresuju.model.entities.database.program.first.DbProgramFirstResults
import cz.nestresuju.model.entities.database.program.second.DbProgramSecondResults
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdActivity
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdResults
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin module providing the database.
 */

val databaseModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    GlobalScope.launch {
                        val database = get<AppDatabase>()

                        database.programFirstDao().updateResults(DbProgramFirstResults())
                        database.programSecondDao().updateResults(DbProgramSecondResults())
                        database.programThirdDao().updateResults(DbProgramThirdResults())
                        addDefaultActivities(androidContext(), database = get())
                    }
                }
            })
            .build().also { it.query("SELECT 1", null) /* Dummy select to trigger onCreate callback */ }
    }
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