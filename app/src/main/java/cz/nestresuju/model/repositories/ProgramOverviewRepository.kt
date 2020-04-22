package cz.nestresuju.model.repositories

import cz.nestresuju.model.converters.ProgramOverviewConverter
import cz.nestresuju.model.database.AppDatabase
import cz.nestresuju.model.entities.domain.program.ProgramId
import cz.nestresuju.model.entities.domain.program.overview.ProgramOverview
import cz.nestresuju.networking.ApiDefinition
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * epository for accessing data related to all program states.
 */
interface ProgramOverviewRepository {

    suspend fun fetchOverview()

    suspend fun observeOverview(): Flow<List<ProgramOverview>>

    suspend fun updateProgramEvaluationState(programId: ProgramId)
}

class ProgramOverviewRepositoryImpl(
    private val apiDefinition: ApiDefinition,
    private val database: AppDatabase,
    private val entityConverter: ProgramOverviewConverter
) : ProgramOverviewRepository {

    override suspend fun fetchOverview() {
        val apiOverview = apiDefinition.getProgramOverview()

        database.programOverviewDao().updateProgramOverview(apiOverview.items.map { entityConverter.apiProgramOverviewToDb(it) })
    }

    override suspend fun observeOverview(): Flow<List<ProgramOverview>> {
        return database.programOverviewDao().observeProgramOverview()
            .map { dbPrograms ->
                dbPrograms.map { dbProgram ->
                    entityConverter.dbProgramOverviewToDomain(dbProgram)
                }
            }
    }

    override suspend fun updateProgramEvaluationState(programId: ProgramId) {
        database.programOverviewDao().setProgramEvaluated(programId.txtId)
    }
}