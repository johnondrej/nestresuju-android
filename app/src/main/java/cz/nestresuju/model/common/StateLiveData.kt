package cz.nestresuju.model.common

import androidx.lifecycle.LiveData

/**
 * Class for easier providing [State] entities with [LiveData].
 */
open class StateLiveData<T>(defaultValue: State<T> = State.Idle) : LiveData<State<T>>(defaultValue) {

    fun loading() {
        value = State.Loading
    }

    fun reloading() {
        val previousValue = (value as? State.Loaded)?.data
        value = State.Reloading(previousValue)
    }

    fun loaded(data: T) {
        value = State.Loaded(data)
    }

    fun empty() {
        value = State.Empty
    }

    fun error(error: Throwable) {
        value = State.Error(error)
    }
}

class EmptyStateLiveData(default: State<Unit> = State.Idle) : StateLiveData<Unit>(default) {

    fun loaded() = super.loaded(Unit)
}