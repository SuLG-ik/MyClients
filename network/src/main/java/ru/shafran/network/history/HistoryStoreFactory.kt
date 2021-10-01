package ru.shafran.network.history

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.shafran.network.card.CardsRepository
import ru.shafran.network.data.card.CardAction


@Suppress("FunctionName")
fun HistoryStore(storeFactory: StoreFactory, cardsRepository: CardsRepository): HistoryStore {
    return HistoryStoreFactory(
        storeFactory = storeFactory,
        cardsRepository = cardsRepository
    ).create()
}

internal class HistoryStoreFactory(
    private val storeFactory: StoreFactory,
    private val cardsRepository: CardsRepository,
) {

    internal fun create(): HistoryStore {
        return object : HistoryStore,
            Store<HistoryStore.Intent, HistoryStore.State, Nothing> by storeFactory.create(
                name = "HistoryStore",
                initialState = HistoryStore.State.Loading,
                reducer = ReducerImpl,
                executorFactory = { HistoryExecutor(cardsRepository) },
            ) {}
    }

    private object ReducerImpl : Reducer<HistoryStore.State, Result> {

        override fun HistoryStore.State.reduce(result: Result): HistoryStore.State {
            return when (result) {
                is Result.Loading -> HistoryStore.State.Loading
                is Result.Error -> HistoryStore.State.Error(result.exception)
                is Result.HistoryLoaded -> HistoryStore.State.HistoryLoaded(
                    page = result.page,
                    history = result.history.sortedByDescending { it.time }
                )
            }
        }
    }

    private class HistoryExecutor(
        private val cardsRepository: CardsRepository,
    ) : SuspendExecutor<HistoryStore.Intent, Nothing, HistoryStore.State, Result, Nothing>(
        Dispatchers.IO) {

        private suspend fun syncDispatch(result: Result) {
            withContext(Dispatchers.Main) { dispatch(result) }
        }

        override suspend fun executeIntent(
            intent: HistoryStore.Intent,
            getState: () -> HistoryStore.State,
        ) {
            when (intent) {
                is HistoryStore.Intent.LoadHistory -> {
                    val history = cardsRepository.getUsagesHistory()
                    syncDispatch(Result.HistoryLoaded(page = intent.page, history = history))
                }
            }
        }
    }


    private sealed class Result {
        object Loading : Result()
        class HistoryLoaded(val page: Int, val history: List<CardAction>) : Result()
        class Error(val exception: Exception) : Result()
    }

}