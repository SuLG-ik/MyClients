package ru.shafran.network.card

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.shafran.network.data.card.Card


@Suppress("FunctionName")
fun CardsStore(storeFactory: StoreFactory, cardsRepository: CardsRepository): CardsStore {
    return CardsStoreFactory(
        storeFactory = storeFactory,
        cardsRepository = cardsRepository
    ).create()
}

internal class CardsStoreFactory(
    private val storeFactory: StoreFactory,
    private val cardsRepository: CardsRepository,
) {

    internal fun create(): CardsStore {
        return object : CardsStore,
            Store<CardsStore.Intent, CardsStore.State, Nothing> by storeFactory.create(
                name = "CardsStore",
                initialState = CardsStore.State.Hidden,
                reducer = ReducerImpl,
                executorFactory = { CardsExecutor(cardsRepository) },
            ) {}
    }

    private object ReducerImpl : Reducer<CardsStore.State, Result> {

        override fun CardsStore.State.reduce(result: Result): CardsStore.State {
            return when (result) {
                is Result.Loading -> CardsStore.State.Loading()
                is Result.CardLoaded -> CardsStore.State.CardLoaded(result.card)
                is Result.Error -> CardsStore.State.Error(result.exception)
                is Result.Hidden -> CardsStore.State.Hidden
                is Result.CardDoesNotExists -> CardsStore.State.CardDoesNotExists
            }
        }
    }

    private class CardsExecutor(
        private val cardsRepository: CardsRepository,
    ) : SuspendExecutor<CardsStore.Intent, Nothing, CardsStore.State, Result, Nothing>(Dispatchers.IO) {

        private suspend fun syncDispatch(result: Result) {
            withContext(Dispatchers.Main) { dispatch(result) }
        }

        override suspend fun executeIntent(
            intent: CardsStore.Intent,
            getState: () -> CardsStore.State
        ) {
            when (intent) {
                is CardsStore.Intent.Hide -> {
                    syncDispatch(Result.Hidden)
                }
                is CardsStore.Intent.LoadCard -> {
                    syncDispatch(Result.Loading)
                    try {
                        val card = cardsRepository.getCardByToken(intent.cardToken)
                        if (card == null) {
                            syncDispatch(Result.CardDoesNotExists)
                            return
                        }
                        syncDispatch(Result.CardLoaded(card))
                    } catch (e: Exception) {
                        syncDispatch(Result.Error(e))
                    }
                }
                is CardsStore.Intent.ActivateCard -> {
                    syncDispatch(Result.Loading)
                    try {
                        val card = cardsRepository.activateCardById(intent.cardId, intent.data)
                        syncDispatch(Result.CardLoaded(card))
                    } catch (e: Exception) {
                        syncDispatch(Result.Error(e))
                    }
                }
                is CardsStore.Intent.DeactivationCard -> {
                    syncDispatch(Result.Loading)
                    try {
                        val card = cardsRepository.deactivateCardById(intent.cardId, intent.data)
                        syncDispatch(Result.CardLoaded(card))
                    } catch (e: Exception) {
                        syncDispatch(Result.Error(e))
                    }
                }
                is CardsStore.Intent.UseCard -> {
                    syncDispatch(Result.Loading)
                    try {
                        val card = cardsRepository.useCardById(intent.cardId, intent.data)
                        syncDispatch(Result.CardLoaded(card))
                    } catch (e: Exception) {
                        syncDispatch(Result.Error(e))
                    }
                }
            }
        }
    }


    private sealed class Result {
        object Hidden : Result()
        object CardDoesNotExists : Result()
        object Loading : Result()
        class CardLoaded(val card: Card) : Result()
        class Error(val exception: Exception) : Result()
    }

}