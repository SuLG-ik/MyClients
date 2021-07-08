@file:UseSerializers(ZonedDateTimeSerializer::class)

package ru.shafran.cards.data.card

import com.arkivanov.decompose.statekeeper.Parcelable
import com.arkivanov.decompose.statekeeper.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.shafran.cards.utils.ZonedDateTimeSerializer
import java.time.ZonedDateTime

@Serializable
sealed class CardAction : Parcelable{

    abstract val time: ZonedDateTime

    @Serializable
    @Parcelize
    class Usage(override val time: ZonedDateTime = ZonedDateTime.now(), val data: UsageData = UsageData()) : CardAction()

    @Serializable
    @Parcelize
    class Activation(override val time: ZonedDateTime = ZonedDateTime.now(), val data: ActivationData = ActivationData()) : CardAction()

    @Serializable
    @Parcelize
    class Deactivation(override val time: ZonedDateTime = ZonedDateTime.now(), val data: DeactivationData = DeactivationData()) : CardAction()

}