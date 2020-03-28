package cz.nestresuju.model.database

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin module providing the database.
 */

val databaseModule = module {

    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, AppDatabase.NAME).build() }
}