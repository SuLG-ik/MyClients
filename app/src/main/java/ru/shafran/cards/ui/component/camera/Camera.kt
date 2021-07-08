package ru.shafran.cards.ui.component.camera

import com.arkivanov.decompose.value.Value
import ru.shafran.cards.ui.component.details.CardDetails

interface Camera {

    val isEnabled: Value<Boolean>

    val details: CardDetails

    fun onDisable()

    fun onEnable()

    suspend fun onQrCodeDetected(rawToken: String)

}