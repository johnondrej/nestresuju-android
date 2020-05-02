package cz.nestresuju.model.entities.api.tests.output

import com.squareup.moshi.JsonClass

/**
 * API entity with data for second final test.
 */
@JsonClass(generateAdapter = true)
class ApiOutputTestSecondResults(
    val stressManagementBefore: Int,
    val stressManagementAfter: Int,
    val wasAppHelpful: Boolean,
    val helpComment: String?,
    val appRating: Int,
    val appRatingComment: String?,
    val recommendation: String?
)