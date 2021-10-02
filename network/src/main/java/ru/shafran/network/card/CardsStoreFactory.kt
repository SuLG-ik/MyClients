package ru.shafran.network.card

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import io.ktor.client.features.*
import io.ktor.http.*
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
                is Result.Loading -> CardsStore.State.Loading
                is Result.CardLoaded -> CardsStore.State.CardLoaded(result.card)
                is Result.Error -> {
                    when (val exception = result.exception) {
                        is ResponseException -> {
                            when (exception.response.status) {
                                HttpStatusCode.InternalServerError -> {
                                    CardsStore.State.Error.InternalServerError
                                }
                                HttpStatusCode.NotFound -> {
                                    CardsStore.State.Error.CardDoesNotExists
                                }
                                HttpStatusCode.Conflict -> {
                                    CardsStore.State.Error.CardMustBeActivated
                                }
                                else -> {
                                    exception.printStackTrace()
                                    CardsStore.State.Error.UnknownError
                                }
                            }
                        }
                        else -> {
                            exception.printStackTrace()
                            CardsStore.State.Error.UnknownError
                        }
                    }
                }
                is Result.Hidden -> CardsStore.State.Hidden
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
            getState: () -> CardsStore.State,
        ) {
            try {
                when (intent) {
                    is CardsStore.Intent.LoadCardByToken -> {
                        syncDispatch(Result.Loading)
                        val card = cardsRepository.getCardByToken(intent.cardToken)
                        syncDispatch(Result.CardLoaded(card))
                    }
                    is CardsStore.Intent.LoadCardByActivationId -> {
                        syncDispatch(Result.Loading)
                        val card = cardsRepository.getCardByActivationId(intent.activationId)
                        syncDispatch(Result.CardLoaded(card))
                    }
                    is CardsStore.Intent.Hide -> {
                        syncDispatch(Result.Hidden)
                    }
                    is CardsStore.Intent.ActivateCard -> {
                        syncDispatch(Result.Loading)
                        val card = cardsRepository.activateCardById(intent.cardId, intent.data)
                        syncDispatch(Result.CardLoaded(card))
                    }
                    is CardsStore.Intent.DeactivationCard -> {
                        syncDispatch(Result.Loading)
                        val card = cardsRepository.deactivateCardById(intent.cardId, intent.data)
                        syncDispatch(Result.CardLoaded(card))
                    }
                    is CardsStore.Intent.UseCard -> {
                        syncDispatch(Result.Loading)
                        val card = cardsRepository.useCardById(intent.cardId, intent.data)
                        syncDispatch(Result.CardLoaded(card))
                    }
                }
            } catch (e: Exception) {
                syncDispatch(Result.Error(e))
            }
        }
    }


    private sealed class Result {
        object Hidden : Result()
        object Loading : Result()
        class CardLoaded(val card: Card) : Result()
        class Error(val exception: Exception) : Result()
    }

}