package cz.nestresuju.screens.diary

import cz.nestresuju.model.entities.domain.StressLevel
import cz.nestresuju.model.entities.domain.StressQuestion

/**
 * Encapsulation for all data related to stress level input.
 */
data class DiaryChoiceInput(val stressLevel: StressLevel, val question: StressQuestion)