package ru.shafran.cards.ui.component.details

import com.arkivanov.decompose.*
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DetectedCard
import ru.shafran.cards.data.card.EmptyDetectedCard
import ru.shafran.cards.data.card.isEmpty
import ru.shafran.cards.ui.component.details.info.CardInfo
import ru.shafran.cards.ui.component.details.info.CardInfoComponent

class CardDetailsComponent(componentContext: ComponentContext) :
    CardDetails, ComponentContext by componentContext {


    override val info: CardInfo = CardInfoComponent(childContext("card_info_component"))

    override val isShown: MutableValue<Boolean> = MutableValue(false)

    override fun onShow(card: DetectedCard) {
        isShown.value = true
        info.onShowCard(card)
    }

    override fun onHide() {
        isShown.value = false
    }


}