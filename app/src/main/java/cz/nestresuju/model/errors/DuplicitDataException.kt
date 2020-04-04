package cz.nestresuju.model.errors

/**
 * Exception thrown when trying to create already existing resource.
 */
class DuplicitDataException : RuntimeException(), DomainException