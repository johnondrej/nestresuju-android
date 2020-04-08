package cz.nestresuju.model.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cz.nestresuju.model.entities.database.program.first.DbProgramFirstResults
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
                        get<AppDatabase>().programFirstDao().updateResults(DbProgramFirstResults())
                    }
                }
            })
            .build().also { it.query("SELECT 1", null) /* Dummy select to trigger onCreate callback */ }
    }
}