package ru.shafran.cards.ui.component.details.info

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import ru.shafran.cards.data.card.Card

class CardInfoComponent(
    componentContext: ComponentContext,
    card: Card,
    private val onUse: (Card) -> Unit,
    private val onActivate: (Card) -> Unit,
    private val onDeactivate: (Card) -> Unit,
) : CardInfo,
    ComponentContext by componentContext {

    override val currentCard: MutableStateFlow<Card> = MutableStateFlow(card)

    override fun onUse() {
        onUse(currentCard.value)
    }

    override fun onActivate() {
        onActivate(currentCard.value)
    }

    override fun onDeactivate() {
        onDeactivate(currentCard.value)
    }


}