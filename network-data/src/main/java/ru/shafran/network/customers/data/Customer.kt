package ru.shafran.network.customers.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.shafran.network.Gender
import ru.shafran.network.PhoneNumber
import ru.shafran.network.utils.ZonedDateTimeSerializer
import java.time.ZonedDateTime

@Serializable
sealed class Customer {

    abstract val id: String

    @Parcelize
    @Serializable
    @SerialName("inactivated")
    data class InactivatedCustomer(
        override val id: String,
    ) : Customer(), Parcelable


    @Parcelize
    @Serializable
    @SerialName("activated")
    data class ActivatedCustomer(
        override val id: String,
        val data: CustomerData,
    ) : Customer(), Parcelable

}


@Parcelize
@Serializable
data class CustomerData(
    val name: String,
    val phone: PhoneNumber?,
    val remark: String,
    val gender: Gender,
    @Serializable(ZonedDateTimeSerializer::class)
    val activationDate: ZonedDateTime = ZonedDateTime.now(),
) : Parcelable
