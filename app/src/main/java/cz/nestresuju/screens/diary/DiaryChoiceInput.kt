package cz.nestresuju.screens.diary

import cz.nestresuju.model.entities.domain.diary.StressLevel
import cz.nestresuju.model.entities.domain.diary.StressQuestion

/**
 * Encapsulation for all data related to stress level input.
 */
data class DiaryChoiceInput(val stressLevel: StressLevel, val question: StressQuestion)