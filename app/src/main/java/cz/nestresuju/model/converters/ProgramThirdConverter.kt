package cz.nestresuju.model.converters

import cz.nestresuju.model.entities.api.program.third.ApiProgramThirdResults
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdActivity
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdHours
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdResults
import cz.nestresuju.model.entities.database.program.third.DbProgramThirdResultsWithActivities
import cz.nestresuju.model.entities.domain.program.third.ProgramThirdResults
import cz.nestresuju.screens.program.third.ProgramThirdConstants
import org.threeten.bp.ZonedDateTime

/**
 * Converter for entities related to program 3.
 */
interface ProgramThirdConverter {

    fun apiProgramThirdResultsToDb(apiResults: ApiProgramThirdResults): DbProgramThirdResultsWithActivities

    fun dbProgramThirdResultsToApi(dbResults: DbProgramThirdResultsWithActivities): ApiProgramThirdResults

    fun dbProgramThirdResultsToDomain(dbResults: DbProgramThirdResultsWithActivities): ProgramThirdResults

    fun programThirdResultsToDb(results: ProgramThirdResults): DbProgramThirdResultsWithActivities
}

class ProgramThirdConverterImpl : ProgramThirdConverter {

    override fun apiProgramThirdResultsToDb(apiResults: ApiProgramThirdResults): DbProgramThirdResultsWithActivities {
        return DbProgramThirdResultsWithActivities(
            results = DbProgramThirdResults(
                target = apiResults.target,
                completion = apiResults.completion,
                satisfiability = apiResults.satisfiability,
                reason = apiResults.reason,
                deadline = apiResults.deadline,
                summarizedTarget = apiResults.summarizedTarget,
                programCompleted = apiResults.programCompletedDate,
                progress = ProgramThirdConstants.PHASES,
                synchronizedWithApi = true
            ), timetable = apiResults.timetable.map { apiHour ->
                val duration = apiHour.time?.toDuration()

                DbProgramThirdHours(
                    name = apiHour.name,
                    hour = duration?.hours,
                    minute = duration?.minutes
                )
            }, activities = apiResults.activities.map { apiActivity ->
                val duration = apiActivity.duration.toDuration()

                DbProgramThirdActivity(
                    name = apiActivity.name,
                    hours = duration.hours,
                    minutes = duration.minutes,
                    userDefined = false
                )
            }
        )
    }

    override fun dbProgramThirdResultsToApi(dbResults: DbProgramThirdResultsWithActivities): ApiProgramThirdResults {
        return ApiProgramThirdResults(
            timetable = dbResults.timetable.map { dbHours ->
                val duration = if (dbHours.hour != null && dbHours.minute != null) Duration(dbHours.hour, dbHours.minute) else null

                ApiProgramThirdResults.ApiHourEntry(
                    name = dbHours.name,
                    time = duration?.toString()
                )
            },
            activities = dbResults.activities.map { dbActivity ->
                val duration = Duration(dbActivity.hours, dbActivity.minutes)

                ApiProgramThirdResults.ApiActivityEntry(
                    name = dbActivity.name,
                    duration = duration.toString()
                )
            }, target = dbResults.results.target,
            completion = dbResults.results.completion,
            satisfiability = dbResults.results.satisfiability,
            reason = dbResults.results.reason,
            deadline = dbResults.results.deadline,
            summarizedTarget = dbResults.results.summarizedTarget,
            programCompletedDate = dbResults.results.programCompleted ?: ZonedDateTime.now()
        )
    }

    override fun dbProgramThirdResultsToDomain(dbResults: DbProgramThirdResultsWithActivities): ProgramThirdResults {
        return ProgramThirdResults(
            timetable = dbResults.timetable.map { dbHours ->
                val duration = if (dbHours.hour != null && dbHours.minute != null) Duration(dbHours.hour, dbHours.minute) else null

                ProgramThirdResults.HourEntry(
                    name = dbHours.name,
                    hour = duration?.hours,
                    minute = duration?.minutes
                )
            }, activities = dbResults.activities.map { dbActivity ->

                ProgramThirdResults.ActivityEntry(
                    name = dbActivity.name,
                    hours = dbActivity.hours,
                    minutes = dbActivity.minutes,
                    userDefined = dbActivity.userDefined
                )
            }, completion = dbResults.results.completion,
            target = dbResults.results.target,
            satisfiability = dbResults.results.satisfiability,
            reason = dbResults.results.reason,
            deadline = dbResults.results.deadline,
            summarizedTarget = dbResults.results.summarizedTarget,
            programCompleted = dbResults.results.programCompleted,
            progress = dbResults.results.progress
        )
    }

    override fun programThirdResultsToDb(results: ProgramThirdResults): DbProgramThirdResultsWithActivities {
        return DbProgramThirdResultsWithActivities(
            results = DbProgramThirdResults(
                target = results.target,
                completion = results.completion,
                satisfiability = results.satisfiability,
                reason = results.reason,
                deadline = results.deadline,
                summarizedTarget = results.summarizedTarget,
                programCompleted = results.programCompleted,
                progress = results.progress,
                synchronizedWithApi = false
            ), timetable = results.timetable.map { hourEntry ->
                DbProgramThirdHours(
                    name = hourEntry.name,
                    hour = hourEntry.hour,
                    minute = hourEntry.minute
                )
            }, activities = results.activities.map { activityEntry ->
                DbProgramThirdActivity(
                    name = activityEntry.name,
                    hours = activityEntry.hours,
                    minutes = activityEntry.minutes,
                    userDefined = activityEntry.userDefined
                )
            }
        )
    }

    private fun String.toDuration(): Duration {
        val split = this.split(':').map { it.toInt() }
        return Duration(hours = split[0], minutes = split[1])
    }

    private data class Duration(val hours: Int, val minutes: Int) {
        override fun toString(): String {
            return String.format("%d:%02d", hours, minutes)
        }
    }
}