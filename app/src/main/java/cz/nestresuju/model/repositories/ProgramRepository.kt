package cz.nestresuju.model.repositories

/**
 * Common interface for all program repositories.
 */
interface ProgramRepository<T> {

    suspend fun fetchProgramResults()

    suspend fun getProgramResults(): T

    suspend fun updateProgramResults(updater: (T) -> T)

    suspend fun submitResults()
}