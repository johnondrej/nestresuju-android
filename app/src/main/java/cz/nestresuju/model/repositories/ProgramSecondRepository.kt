package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.ProgramSecondConverter
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.domain.program.second.ProgramSecondResults
import cz.nestresuju.model.synchronization.DataSynchronizer
import cz.nestresuju.networking.ApiDefinition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.ZonedDateTime

/**
 * Repository for accessing data related to second program.
 */
interface ProgramSecondRepository : ProgramRepository<ProgramSecondResults>

class ProgramSecondRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val database: AppDatabase,
    private val dataSynchronizer: DataSynchronizer,
    private val entityConverter: ProgramSecondConverter
) : ProgramSecondRepository {

    override suspend fun fetchProgramResults() {
        val apiResults = apiDefinition.getSecondProgramResults()
        database.programSecondDao().updateResults(entityConverter.apiProgramSecondResultsToDb(apiResults))
    }

    override suspend fun getProgramResults() = entityConverter.dbProgramSecondResultsToDomain(database.programSecondDao().getResults())

    override suspend fun observeProgramResults(): Flow<ProgramSecondResults> {
        return database.programSecondDao().observeResults()
            .map { dbResults -> entityConverter.dbProgramSecondResultsToDomain(dbResults) }
    }

    override suspend fun updateProgramResults(updater: (ProgramSecondResults) -> ProgramSecondResults) {
        database.programSecondDao().updateResults(entityConverter.programSecondResultsToDb(updater(getProgramResults())))
    }

    override suspend fun submitResults() {
        val programDao = database.programSecondDao()
        val currentResults = programDao.getResults()

        programDao.updateResults(currentResults.copy(programCompleted = ZonedDateTime.now()))
        dataSynchronizer.synchronizeProgram()
    }
}