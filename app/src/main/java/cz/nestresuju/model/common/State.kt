package cz.nestresuju.model.common

/**
 * Represents state of data.
 */
sealed class State<out T> {

    object Idle : State<Nothing>()
    object Loading : State<Nothing>()
    data class Reloading<out T>(val previousData: T? = null) : State<T>()
    data class Loaded<out T>(val data: T) : State<T>()
    data class Error(val error: Throwable) : State<Nothing>()
}