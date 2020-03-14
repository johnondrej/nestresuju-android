package cz.nestresuju.common

import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import cz.nestresuju.model.common.StateLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Base [ViewModel] with support for error handling and common stuff.
 */
abstract class BaseViewModel : ViewModel() {

    val errorStream = LiveEvent<Throwable>()

    protected fun CoroutineScope.launchWithErrorHandling(
        context: CoroutineContext = EmptyCoroutineContext,
        errorPropagationStreams: Array<StateLiveData<*>> = emptyArray(),
        block: suspend CoroutineScope.() -> Unit
    ): Job = launch(context) {
        try {
            block()
        } catch (error: Throwable) {
            handleError(error, errorPropagationStreams)
        }
    }

    protected open fun handleError(error: Throwable, streams: Array<StateLiveData<*>> = emptyArray()) {
        errorStream.value = error
        streams.forEach { it.error(error) }
    }
}