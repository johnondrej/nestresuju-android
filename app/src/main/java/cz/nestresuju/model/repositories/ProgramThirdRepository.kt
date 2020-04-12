package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.ProgramThirdConverter
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdActivity
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdHours
import cz.nestresuju.model.entities.domain.program.third.ProgramThirdResults
import cz.nestresuju.model.synchronization.DataSynchronizer
import cz.nestresuju.networking.ApiDefinition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.ZonedDateTime

/**
 * Repository for accessing data related to third program.
 */
interface ProgramThirdRepository : ProgramRepository<ProgramThirdResults> {

    suspend fun updateTimetableActivity(name: String, hour: Int?, minute: Int?)

    suspend fun updateActivity(name: String, userDefined: Boolean, hours: Int, minutes: Int)

    suspend fun removeActivity(name: String)
}

class ProgramThirdRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val database: AppDatabase,
    private val dataSynchronizer: DataSynchronizer,
    private val entityConverter: ProgramThirdConverter
) : ProgramThirdRepository {

    override suspend fun fetchProgramResults() {
        val apiResults = apiDefinition.getThirdProgramResults()
        database.programThirdDao().updateResults(entityConverter.apiProgramThirdResultsToDb(apiResults))
    }

    override suspend fun getProgramResults() = entityConverter.dbProgramThirdResultsToDomain(database.programThirdDao().getFullResults())

    override suspend fun observeProgramResults(): Flow<ProgramThirdResults> {
        return database.programThirdDao().observeResults()
            .map { dbResults -> entityConverter.dbProgramThirdResultsToDomain(dbResults) }
    }

    override suspend fun updateProgramResults(updater: (ProgramThirdResults) -> ProgramThirdResults) {
        database.programThirdDao().updateResults(entityConverter.programThirdResultsToDb(updater(getProgramResults())))
    }

    override suspend fun submitResults() {
        val programDao = database.programThirdDao()
        val currentResults = programDao.getFullResults()

        programDao.updateResults(currentResults.copy(results = currentResults.results.copy(programCompleted = ZonedDateTime.now())))
        dataSynchronizer.synchronizeProgram()
    }

    override suspend fun updateTimetableActivity(name: String, hour: Int?, minute: Int?) {
        database.programThirdDao().updateTimetable(
            DbProgramThirdHours(
                name = name,
                hour = hour,
                minute = minute
            )
        )
    }

    override suspend fun updateActivity(name: String, userDefined: Boolean, hours: Int, minutes: Int) {
        database.programThirdDao().updateActivity(
            DbProgramThirdActivity(
                name = name,
                hours = hours,
                minutes = minutes,
                userDefined = userDefined
            )
        )
    }

    override suspend fun removeActivity(name: String) {
        database.programThirdDao().removeActivity(name)
    }
}