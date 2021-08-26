@file:UseSerializers(ZonedDateTimeSerializer::class)

package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.shafran.cards.utils.ZonedDateTimeSerializer
import java.time.ZonedDateTime


@Serializable
sealed class CardAction: Parcelable {

    abstract val id: Long
    abstract val time: ZonedDateTime

    @Serializable
    @Parcelize
    @SerialName("activation")
    class Activation(
        val data: ActivationData,
        override val time: ZonedDateTime = ZonedDateTime.now(),
        override val id: Long,
    ) : CardAction()

    @Serializable
    @Parcelize
    @SerialName("usage")
    class Usage(
        override val time: ZonedDateTime = ZonedDateTime.now(),
        val activationId: Long,
        val data: UsageData,
        override val id: Long,
    ) : CardAction()

    @Serializable
    @Parcelize
    @SerialName("deactivation")
    class Deactivation(
        override val time: ZonedDateTime = ZonedDateTime.now(),
        val activationId: Long,
        val data: DeactivationData = DeactivationData(),
        override val id: Long,
    ) : CardAction()

}