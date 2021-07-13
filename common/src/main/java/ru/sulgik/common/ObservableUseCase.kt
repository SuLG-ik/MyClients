package ru.sulgik.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Suppress("FunctionName")
fun <T, P> ObservableUseCase(
    useCase: UseCase<T, P>,
    coroutineScope: CoroutineScope,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
): ObservableUseCase<T, P> = ObservableUseCaseImpl(useCase, coroutineScope, coroutineContext)

interface ObservableUseCase<T, P> {

    val results: StateFlow<Result<T>?>

    fun execute(parameter: P): Boolean

}