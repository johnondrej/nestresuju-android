package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.ProgramFirstConverter
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.domain.program.first.ProgramFirstResults
import cz.nestresuju.model.synchronization.DataSynchronizer
import cz.nestresuju.networking.ApiDefinition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.ZonedDateTime

/**
 * Repository for accessing data related to first program.
 */
interface ProgramFirstRepository : ProgramRepository<ProgramFirstResults>

class ProgramFirstRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val database: AppDatabase,
    private val dataSynchronizer: DataSynchronizer,
    private val entityConverter: ProgramFirstConverter
) : ProgramFirstRepository {

    override suspend fun fetchProgramResults() {
        val apiResults = apiDefinition.getFirstProgramResults()
        database.programFirstDao().updateResults(entityConverter.apiProgramFirstResultsToDb(apiResults))
    }

    override suspend fun getProgramResults() = entityConverter.dbProgramFirstResultsToDomain(database.programFirstDao().getResults())

    override suspend fun observeProgramResults(): Flow<ProgramFirstResults> {
        return database.programFirstDao().observeResults()
            .map { dbResults -> entityConverter.dbProgramFirstResultsToDomain(dbResults) }
    }

    override suspend fun updateProgramResults(updater: (ProgramFirstResults) -> ProgramFirstResults) {
        database.programFirstDao().updateResults(entityConverter.programFirstResultsToDb(updater(getProgramResults())))
    }

    override suspend fun submitResults(programCompletedDate: ZonedDateTime) {
        val programDao = database.programFirstDao()
        val currentResults = programDao.getResults()

        programDao.updateResults(currentResults.copy(programCompleted = programCompletedDate))
        dataSynchronizer.synchronizeProgram()
    }
}