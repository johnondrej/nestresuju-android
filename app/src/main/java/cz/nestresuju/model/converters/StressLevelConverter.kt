package cz.nestresuju.model.converters

import cz.nestresuju.model.entities.domain.StressLevel

/**
 * Converter for [StressLevel] enum
 */
interface StressLevelConverter {

    fun intToStressLevel(intValue: Int): StressLevel

    fun stressLevelToInt(stressLevel: StressLevel): Int
}

class StressLevelConverterImpl : StressLevelConverter {

    override fun intToStressLevel(intValue: Int): StressLevel {
        return when (intValue) {
            0 -> StressLevel.NONE
            1 -> StressLevel.STRESSED
            2 -> StressLevel.BAD
            3 -> StressLevel.GOOD
            4 -> StressLevel.GREAT
            else -> throw IllegalArgumentException("Invalid stress level int value $intValue")
        }
    }

    override fun stressLevelToInt(stressLevel: StressLevel): Int {
        return when (stressLevel) {
            StressLevel.NONE -> 0
            StressLevel.STRESSED -> 1
            StressLevel.BAD -> 2
            StressLevel.GOOD -> 3
            StressLevel.GREAT -> 4
        }
    }
}