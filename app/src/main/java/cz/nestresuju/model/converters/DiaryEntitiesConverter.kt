package cz.nestresuju.model.converters

import cz.nestresuju.common.extensions.toZonedDateTime
import cz.nestresuju.model.entities.api.diary.ApiDiaryEntry
import cz.nestresuju.model.entities.api.diary.MoodQuestionsResponse
import cz.nestresuju.model.entities.database.diary.DbDiaryEntry
import cz.nestresuju.model.entities.database.diary.DbDiaryEntryWithQuestion
import cz.nestresuju.model.entities.database.diary.DbStressQuestion
import cz.nestresuju.model.entities.domain.diary.DiaryEntry
import cz.nestresuju.model.entities.domain.diary.StressQuestion

/**
 * Converter for converting API & DB & domain entities for diary.
 */
interface DiaryEntitiesConverter : StressLevelConverter {

    fun dbStressQuestionToDomain(dbStressQuestion: DbStressQuestion): StressQuestion

    fun apiStressQuestionToDb(apiStressQuestion: MoodQuestionsResponse.ApiStressQuestion): DbStressQuestion

    fun apiDiaryEntryToDb(apiDiaryEntry: ApiDiaryEntry): DbDiaryEntry

    fun dbDiaryEntryToDomain(dbEntry: DbDiaryEntryWithQuestion): DiaryEntry

    fun diaryEntryToDb(diaryEntry: DiaryEntry.StressLevelEntry): DbDiaryEntry

    fun diaryEntryToDb(diaryEntry: DiaryEntry.NoteEntry): DbDiaryEntry
}

class DiaryEntitiesConverterImpl(
    private val stressLevelConverter: StressLevelConverter
) : DiaryEntitiesConverter, StressLevelConverter by stressLevelConverter {

    companion object {

        private const val ENTRY_TYPE_STRESS_LEVEL = 1
        private const val ENTRY_TYPE_NOTE = 2
    }

    override fun dbStressQuestionToDomain(dbStressQuestion: DbStressQuestion): StressQuestion {
        return StressQuestion(
            id = dbStressQuestion.id,
            stressLevel = stressLevelConverter.intToStressLevel(dbStressQuestion.stressLevel),
            text = dbStressQuestion.text
        )
    }

    override fun apiStressQuestionToDb(apiStressQuestion: MoodQuestionsResponse.ApiStressQuestion): DbStressQuestion {
        return DbStressQuestion(
            id = apiStressQuestion.id,
            stressLevel = apiStressQuestion.moodLevel,
            text = apiStressQuestion.text
        )
    }

    override fun apiDiaryEntryToDb(apiDiaryEntry: ApiDiaryEntry): DbDiaryEntry {
        return DbDiaryEntry(
            id = apiDiaryEntry.id,
            entryType = apiDiaryEntry.entryType,
            moodLevel = apiDiaryEntry.moodLevel,
            questionId = apiDiaryEntry.questionId,
            text = apiDiaryEntry.text,
            dateCreated = apiDiaryEntry.dateCreated,
            dateModified = apiDiaryEntry.dateModified
        )
    }

    override fun dbDiaryEntryToDomain(dbEntry: DbDiaryEntryWithQuestion): DiaryEntry {
        return when (dbEntry.diaryEntry.entryType) {
            ENTRY_TYPE_STRESS_LEVEL -> DiaryEntry.StressLevelEntry(
                id = dbEntry.diaryEntry.id,
                stressLevel = stressLevelConverter.intToStressLevel(dbEntry.diaryEntry.moodLevel!!),
                question = dbStressQuestionToDomain(dbEntry.stressQuestion!!),
                answer = dbEntry.diaryEntry.text,
                dateCreated = dbEntry.diaryEntry.dateCreated.toLocalDateTime(),
                dateModified = dbEntry.diaryEntry.dateModified.toLocalDateTime()
            )
            ENTRY_TYPE_NOTE -> DiaryEntry.NoteEntry(
                id = dbEntry.diaryEntry.id,
                text = dbEntry.diaryEntry.text,
                dateCreated = dbEntry.diaryEntry.dateCreated.toLocalDateTime(),
                dateModified = dbEntry.diaryEntry.dateModified.toLocalDateTime()
            )
            else -> throw IllegalArgumentException("Invalid diary entry type ${dbEntry.diaryEntry.entryType}")
        }
    }

    override fun diaryEntryToDb(diaryEntry: DiaryEntry.StressLevelEntry): DbDiaryEntry {
        return DbDiaryEntry(
            id = diaryEntry.id,
            entryType = ENTRY_TYPE_STRESS_LEVEL,
            moodLevel = stressLevelConverter.stressLevelToInt(diaryEntry.stressLevel),
            questionId = diaryEntry.question.id,
            text = diaryEntry.answer,
            dateCreated = diaryEntry.dateCreated.toZonedDateTime(),
            dateModified = diaryEntry.dateModified.toZonedDateTime()
        )
    }

    override fun diaryEntryToDb(diaryEntry: DiaryEntry.NoteEntry): DbDiaryEntry {
        return DbDiaryEntry(
            id = diaryEntry.id,
            entryType = ENTRY_TYPE_NOTE,
            moodLevel = null,
            questionId = null,
            text = diaryEntry.text,
            dateCreated = diaryEntry.dateCreated.toZonedDateTime(),
            dateModified = diaryEntry.dateModified.toZonedDateTime()
        )
    }
}