package cz.nestresuju.networking

/**
 * Interface for classes responsible for exception mapping.
 */
interface ApiExceptionMapper {

    fun mapException(exception: Throwable): Throwable
}