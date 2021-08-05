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
sealed class CardAction : Parcelable {

    abstract val time: ZonedDateTime

    @Serializable
    @SerialName("usage")
    @Parcelize
    class Usage(
        override val time: ZonedDateTime = ZonedDateTime.now(),
        val data: UsageData = UsageData(),
    ) : CardAction()

    @Serializable
    @SerialName("activation")
    @Parcelize
    class Activation(
        override val time: ZonedDateTime = ZonedDateTime.now(),
        val data: ActivationData = ActivationData(capacity = 5),
    ) : CardAction()

    @Serializable
    @SerialName("deactivation")
    @Parcelize
    class Deactivation(
        override val time: ZonedDateTime = ZonedDateTime.now(),
        val data: DeactivationData = DeactivationData(),
    ) : CardAction()

}