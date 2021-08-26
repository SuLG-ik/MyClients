package ru.shafran.cards.ui.component.cards

import com.arkivanov.decompose.*
import com.arkivanov.decompose.value.Value
import ru.shafran.cards.data.card.Card
import ru.shafran.cards.data.card.DetectedCard
import ru.shafran.cards.ui.component.camera.CameraComponent
import ru.shafran.cards.ui.component.details.CardDetails
import ru.shafran.cards.ui.component.details.CardDetailsComponent
import ru.shafran.cards.ui.component.usages.UsagesListComponent

class CardsComponent(
    componentContext: ComponentContext,
) : Cards, ComponentContext by componentContext {


    override val details: CardDetails = CardDetailsComponent(childContext("details_component"))

    private val router =
        router<CardsConfiguration, Cards.Child>(initialConfiguration = CardsConfiguration.UsagesList,
            childFactory = this::createChild, handleBackButton = true)
    override val routerState: Value<RouterState<CardsConfiguration, Cards.Child>> = router.state

    private fun createChild(configuration: CardsConfiguration, componentContext: ComponentContext): Cards.Child {
        return when(configuration) {
            CardsConfiguration.Camera -> Cards.Child.Camera(CameraComponent(
                componentContext,
                onDetected = this::onShowCard,
                isDetailShown = details.isShown
            ))
            CardsConfiguration.UsagesList -> Cards.Child.UsagesList(UsagesListComponent())
        }
    }

    override fun onTakeCard() {
        router.push(CardsConfiguration.Camera)
    }

    override fun onShowCard(card: DetectedCard) {
        details.onShow(card)
    }

    override fun onShowCard(card: Card) {
        details.onShow(card)
    }

}