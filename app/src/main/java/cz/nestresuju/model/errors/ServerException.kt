package cz.nestresuju.model.errors

/**
 * Unknown HTTP exception with status code.
 */
class ServerException(
    val httpCode: Int,
    val errorCode: Int?,
    val description: String?
) : RuntimeException()