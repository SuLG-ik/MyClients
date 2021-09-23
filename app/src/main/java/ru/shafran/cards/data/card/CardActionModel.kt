package ru.shafran.cards.data.card

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import ru.shafran.network.data.card.CardAction
import java.time.ZonedDateTime

sealed class CardActionModel: Parcelable {

    abstract val id: Long
    abstract val time: ZonedDateTime

    @Parcelize
    class Activation(
        val data: ActivationDataModel,
        override val time: ZonedDateTime = ZonedDateTime.now(),
        override val id: Long,
    ) : CardActionModel()


    @Parcelize
    class Usage(
        override val time: ZonedDateTime = ZonedDateTime.now(),
        val activationId: Long,
        val data: UsageDataModel,
        override val id: Long,
    ) : CardActionModel()


    @Parcelize
    class Deactivation(
        override val time: ZonedDateTime = ZonedDateTime.now(),
        val activationId: Long,
        val data: DeactivationDataModel = DeactivationDataModel(),
        override val id: Long,
    ) : CardActionModel()

}

fun CardActionModel.toData(): CardAction {
    return when (this) {
        is CardActionModel.Activation -> CardAction.Activation(
            data = data.toData(), time = time, id = id,
        )
        is CardActionModel.Deactivation -> CardAction.Deactivation(
            data = data.toData(), time = time, id = id, activationId = activationId,
        )
        is CardActionModel.Usage  -> CardAction.Usage(
            data = data.toData(), time= time, id = id, activationId = activationId,
        )
    }
}

fun CardAction.toModel(): CardActionModel {
    return when (this) {
        is CardAction.Activation -> CardActionModel.Activation(
            data = data.toModel(), time = time, id = id,
        )
        is CardAction.Deactivation -> CardActionModel.Deactivation(
            data = data.toModel(), time = time, id = id, activationId = activationId,
        )
        is CardAction.Usage  -> CardActionModel.Usage(
            data = data.toModel(), time= time, id = id, activationId = activationId,
        )
    }
}
