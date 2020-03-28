package cz.nestresuju.model.errors.constants

/**
 * Constants representing inner error codes from error response body.
 */
object ErrorResponseCodes {

    const val NOT_AUTHORIZED = 100
    const val NOT_SECURE = 101
    const val USER_DISABLED = 102
    const val NOT_ACTIVATED = 103
    const val SERVICE_DEACTIVATED = 200
    const val ITEM_NOT_FOUND = 300
    const val INVALID_DATA = 400
    const val INVALID_VALUE = 401
    const val MISSING_VALUE = 402
    const val DUPLICIT_DATA = 403
}