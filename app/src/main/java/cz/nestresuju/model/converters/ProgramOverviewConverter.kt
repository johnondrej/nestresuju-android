package cz.nestresuju.model.converters

import cz.nestresuju.model.entities.api.program.overview.ApiProgramOverview
import cz.nestresuju.model.entities.database.program.overview.DbProgramOverview
import cz.nestresuju.model.entities.domain.program.overview.ProgramOverview

/**
 * Converter of entities containing information about program state.
 */
interface ProgramOverviewConverter {

    fun dbProgramOverviewToDomain(dbProgramOverview: DbProgramOverview): ProgramOverview

    fun programOverviewToDb(programOverview: ProgramOverview): DbProgramOverview

    fun apiProgramOverviewToDb(apiProgramOverview: ApiProgramOverview): DbProgramOverview
}

class ProgramOverviewConverterImpl : ProgramOverviewConverter {

    override fun dbProgramOverviewToDomain(dbProgramOverview: DbProgramOverview): ProgramOverview {
        return ProgramOverview(
            id = dbProgramOverview.id,
            name = dbProgramOverview.name,
            completed = dbProgramOverview.completed,
            evaluated = dbProgramOverview.evaluated,
            startDate = dbProgramOverview.startDate,
            order = dbProgramOverview.order
        )
    }

    override fun programOverviewToDb(programOverview: ProgramOverview): DbProgramOverview {
        return DbProgramOverview(
            id = programOverview.id,
            name = programOverview.name,
            completed = programOverview.completed,
            evaluated = programOverview.evaluated,
            startDate = programOverview.startDate,
            order = programOverview.order
        )
    }

    override fun apiProgramOverviewToDb(apiProgramOverview: ApiProgramOverview): DbProgramOverview {
        return DbProgramOverview(
            id = apiProgramOverview.title,
            name = apiProgramOverview.name,
            completed = apiProgramOverview.isCompleted,
            evaluated = apiProgramOverview.isEvaluated,
            startDate = apiProgramOverview.startDate,
            order = apiProgramOverview.order
        )
    }
}