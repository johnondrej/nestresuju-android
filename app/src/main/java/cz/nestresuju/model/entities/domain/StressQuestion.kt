package cz.nestresuju.model.entities.domain

/**
 * Entity representing question shown to user for specific mood level.
 */
data class StressQuestion(
    val id: Long,
    val stressLevel: StressLevel,
    val text: String
)