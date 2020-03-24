package cz.nestresuju.model.entities.api

/**
 * Interface for all responses from API that are constrained with timestamp.
 */
interface TimestampedResponse {

    val timestamp: Long
}