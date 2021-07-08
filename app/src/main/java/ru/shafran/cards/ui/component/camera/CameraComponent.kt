package ru.shafran.cards.ui.component.camera

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.reduce
import ru.shafran.cards.data.card.DetectedCard
import ru.shafran.cards.ui.component.details.CardDetails
import ru.shafran.cards.ui.component.details.CardDetailsComponent

class CameraComponent(componentContext: ComponentContext) : Camera, ComponentContext by componentContext {

    override val isEnabled: MutableValue<Boolean> = MutableValue(false)
    override val details: CardDetails = CardDetailsComponent(childContext("details_component"))

    override fun onDisable() {
        isEnabled.reduce { false }
    }

    override fun onEnable() {
        isEnabled.reduce { true }
    }

    override suspend fun onQrCodeDetected(rawToken: String) {
        details.onShow(DetectedCard(rawToken))
    }

}