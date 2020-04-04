package cz.nestresuju.model.errors

/**
 * Unknown exception.
 */
class UnknownException(val error: Throwable) : RuntimeException(error), DomainException