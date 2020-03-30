package cz.nestresuju.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.nestresuju.model.database.converters.RoomTypeConverters
import cz.nestresuju.model.database.dao.DiaryDao
import cz.nestresuju.model.database.dao.SynchronizerDao
import cz.nestresuju.model.entities.database.diary.DbDiaryEntry
import cz.nestresuju.model.entities.database.diary.DbStressQuestion
import cz.nestresuju.model.entities.database.diary.SynchronizerDbDiaryChange

/**
 * Class representing app database.
 */
@Database(
    entities = [DbDiaryEntry::class, DbStressQuestion::class, SynchronizerDbDiaryChange::class],
    version = 1
)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        const val NAME = "nestresuju_db"
    }

    abstract fun diaryDao(): DiaryDao

    abstract fun synchronizerDao(): SynchronizerDao
}