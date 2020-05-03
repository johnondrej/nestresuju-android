package cz.nestresuju.model.entities.domain.auth

/**
 * Data about completion of necessary after-login steps.
 */
data class LoginChecklistCompletion(
    val consentGiven: Boolean,
    val inputTestSubmitted: Boolean,
    val screeningTestSubmitted: Boolean,
    val finalTestFirstSubmitted: Boolean,
    val finalTestSecondSubmitted: Boolean
)