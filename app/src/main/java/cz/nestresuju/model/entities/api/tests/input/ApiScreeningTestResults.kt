package cz.nestresuju.model.entities.api.tests.input

import com.squareup.moshi.JsonClass
import org.threeten.bp.ZonedDateTime

/**
 * Request entity for submitting screening test results.
 */
@JsonClass(generateAdapter = true)
class ApiScreeningTestResults(
    val selectedOptionIds: List<Long>,
    val programCompletedDate: ZonedDateTime
)