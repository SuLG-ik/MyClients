package ru.shafran.cards.ui.component.history

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.shafran.cards.data.card.CardActionModel
import ru.shafran.cards.data.card.toModel
import ru.shafran.cards.ui.component.cardsdetails.CardDetails
import ru.shafran.cards.ui.component.cardsdetails.CardDetailsComponent
import ru.shafran.cards.utils.get
import ru.shafran.cards.utils.stores
import ru.shafran.network.card.CardsStore
import ru.shafran.network.employee.EmployeesStore
import ru.shafran.network.history.HistoryStore

class HistoryComponent(componentContext: ComponentContext) : History,
    ComponentContext by componentContext {


    private val cardsStore by stores { CardsStore(get(), get()) }
    private val employeesStore by stores { EmployeesStore(get(), get()) }
    override val cardDetails: CardDetails = CardDetailsComponent(
        childContext("card_details"),
        cardsStore = cardsStore,
        employeesStore = employeesStore
    )

    private val cardsRepository by stores { HistoryStore(get(), get()) }

    override fun onChooseUsage(activationId: Long) {
        cardDetails.onShowByActivationId(activationId)
    }

    override val history: Flow<List<CardActionModel>?> = cardsRepository.states.map { state ->
        when (state) {
            is HistoryStore.State.Loading -> {
                null
            }
            is HistoryStore.State.HistoryLoaded -> {
                state.history.map {
                    it.toModel()
                }
            }
            else -> {
                null
            }
        }
    }

    init {
        onUpdate()
    }

    override fun onUpdate() {
        cardsRepository.accept(HistoryStore.Intent.LoadHistory())
    }

}