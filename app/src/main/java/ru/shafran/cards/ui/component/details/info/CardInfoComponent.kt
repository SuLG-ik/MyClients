package ru.shafran.cards.ui.component.details.info

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import ru.shafran.cards.data.card.CardModel

class CardInfoComponent(
    componentContext: ComponentContext,
    card: CardModel,
    private val onUse: (CardModel) -> Unit,
    private val onActivate: (CardModel) -> Unit,
    private val onDeactivate: (CardModel) -> Unit,
) : CardInfo,
    ComponentContext by componentContext {

    override val currentCard: MutableStateFlow<CardModel> = MutableStateFlow(card)

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