package ru.sulgik.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class ObservableUseCaseImpl<T, P>(
    private val useCase: UseCase<T, P>,
    private val coroutineScope: CoroutineScope,
    private val coroutineContext: CoroutineContext,
) : ObservableUseCase<T, P> {
    private val _results = MutableStateFlow<Result<T>?>(null)

    override val results: StateFlow<Result<T>?> = _results.asStateFlow()

    val isEnabled get() = job != null

    private var job: Job? = null

    override fun execute(parameter: P): Boolean {
        if (isEnabled) return false
        executeCoroutine(parameter)
        return true
    }

    private fun executeCoroutine(parameter: P) {
        job = coroutineScope.launch(coroutineContext) {
            emitSuccess(useCase.run(parameter = parameter))
        }.apply {
            invokeOnCompletion {
                if (it != null) {
                    emitFailure(it)
                }
                job = null
            }
        }
    }

    private fun emitLoading() {
        _results.value = null
    }

    private fun emitSuccess(result: T) {
        _results.value = Result.success(result)
    }

    private fun emitFailure(throwable: Throwable) {
        _results.value = Result.failure(throwable)
    }


}