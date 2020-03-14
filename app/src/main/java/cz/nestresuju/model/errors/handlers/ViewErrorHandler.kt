package cz.nestresuju.model.errors.handlers

/**
 * Interface for error handling in view with type [T].
 */
interface ViewErrorHandler<T> {

    /**
     * Function that handles given [error]. Returns true if error was handled and "consumed", so it's not passed to another handlers.
     */
    fun handleError(handlingView: T, error: Throwable): Boolean
}