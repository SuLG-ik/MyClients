@file:UseSerializers(ZonedDateTimeSerializer::class)

package ru.shafran.network.data.card

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.shafran.network.utils.ZonedDateTimeSerializer
import java.time.ZonedDateTime

@Serializable
sealed class CardAction {

    abstract val id: Long
    abstract val time: ZonedDateTime

    @Serializable
    @SerialName("activation")
    class Activation(
        val data: ActivationData,
        override val time: ZonedDateTime = ZonedDateTime.now(),
        val cardId: Long,
        override val id: Long,
    ) : CardAction()

    @Serializable
    @SerialName("usage")
    class Usage(
        override val time: ZonedDateTime = ZonedDateTime.now(),
        val activationId: Long,
        val data: UsageData,
        override val id: Long,
    ) : CardAction()

    @Serializable
    @SerialName("deactivation")
    class Deactivation(
        override val time: ZonedDateTime = ZonedDateTime.now(),
        val activationId: Long,
        val data: DeactivationData = DeactivationData(),
        override val id: Long,
    ) : CardAction()

}