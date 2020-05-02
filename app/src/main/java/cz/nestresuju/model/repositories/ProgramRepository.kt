package cz.nestresuju.model.repositories

import kotlinx.coroutines.flow.Flow
import org.threeten.bp.ZonedDateTime

/**
 * Common interface for all program repositories.
 */
interface ProgramRepository<T> {

    suspend fun fetchProgramResults()

    suspend fun getProgramResults(): T

    suspend fun observeProgramResults(): Flow<T>

    suspend fun updateProgramResults(updater: (T) -> T)

    suspend fun submitResults(programCompletedDate: ZonedDateTime)
}