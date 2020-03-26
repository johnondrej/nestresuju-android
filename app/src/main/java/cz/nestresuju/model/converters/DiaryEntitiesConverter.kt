package cz.nestresuju.model.converters

import cz.nestresuju.model.entities.api.diary.ApiDiaryEntry
import cz.nestresuju.model.entities.api.diary.MoodQuestionsResponse
import cz.nestresuju.model.entities.domain.DiaryEntry
import cz.nestresuju.model.entities.domain.StressQuestion

/**
 * Converter for converting API & DB & domain entities for diary.
 */
interface DiaryEntitiesConverter {

    fun apiMoodQuestionToDomain(apiStressQuestion: MoodQuestionsResponse.ApiStressQuestion): StressQuestion

    fun apiDiaryEntryToDomain(apiDiaryEntry: ApiDiaryEntry, questions: List<StressQuestion>): DiaryEntry
}

class DiaryEntitiesConverterImpl(private val stressLevelConverter: StressLevelConverter) : DiaryEntitiesConverter {

    companion object {

        private const val ENTRY_TYPE_STRESS_LEVEL = 1
        private const val ENTRY_TYPE_NOTE = 2
    }

    override fun apiMoodQuestionToDomain(apiStressQuestion: MoodQuestionsResponse.ApiStressQuestion): StressQuestion {
        return StressQuestion(
            id = apiStressQuestion.id,
            stressLevel = stressLevelConverter.intToStressLevel(apiStressQuestion.moodLevel),
            text = apiStressQuestion.text
        )
    }

    override fun apiDiaryEntryToDomain(apiDiaryEntry: ApiDiaryEntry, questions: List<StressQuestion>): DiaryEntry {
        return when (apiDiaryEntry.entryType) {
            ENTRY_TYPE_STRESS_LEVEL -> DiaryEntry.StressLevelEntry(
                id = apiDiaryEntry.id,
                stressLevel = stressLevelConverter.intToStressLevel(apiDiaryEntry.moodLevel!!),
                question = questions.first { it.id == apiDiaryEntry.questionId!! },
                answer = apiDiaryEntry.text,
                dateCreated = apiDiaryEntry.dateCreated,
                dateModified = apiDiaryEntry.dateModified
            )
            ENTRY_TYPE_NOTE -> DiaryEntry.NoteEntry(
                id = apiDiaryEntry.id,
                text = apiDiaryEntry.text,
                dateCreated = apiDiaryEntry.dateCreated,
                dateModified = apiDiaryEntry.dateModified
            )
            else -> throw IllegalArgumentException("Invalid diary entry type ${apiDiaryEntry.entryType}")
        }
    }
}