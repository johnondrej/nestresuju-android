package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.ProgramFirstEntitiesConverter
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.domain.program.first.ProgramFirstResults
import cz.nestresuju.networking.ApiDefinition

/**
 * Repository for accessing data related to first program.
 */
interface ProgramFirstRepository : ProgramRepository<ProgramFirstResults>

class ProgramFirstRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val database: AppDatabase,
    private val entityConverter: ProgramFirstEntitiesConverter
) : ProgramFirstRepository {

    override suspend fun fetchProgramResults() {
        val apiResults = apiDefinition.getFirstProgramResults()
        database.programFirstDao().updateResults(entityConverter.apiProgramFirstResultsToDb(apiResults))
    }

    override suspend fun getProgramResults() = entityConverter.dbProgramFirstResultsToDomain(database.programFirstDao().getResults())

    override suspend fun updateProgramResults(updater: (ProgramFirstResults) -> ProgramFirstResults) {
        database.programFirstDao().updateResults(entityConverter.programFirstResultsToDb(updater(getProgramResults())))
    }

    override suspend fun submitResults() {
        apiDefinition.submitFirstProgramResults(entityConverter.dbProgramFirstResultsToApi(database.programFirstDao().getResults()))
    }
}