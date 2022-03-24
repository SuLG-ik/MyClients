@file:UseSerializers(ZonedDateTimeSerializer::class)

package ru.shafran.network.session.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.shafran.network.employees.data.Employee
import ru.shafran.network.services.data.ConfiguredService
import ru.shafran.network.utils.ZonedDateTimeSerializer
import java.time.ZonedDateTime

@Serializable
@Parcelize
data class Session(
    val id: String,
    val activation: SessionActivation,
    val usages: List<SessionUsage>,
    val manualDeactivation: SessionManualDeactivation?,
) : Parcelable {
    val isDeactivated: Boolean
        get() {
            return usages.size >= activation.service.configuration.amount || manualDeactivation != null
        }
}

@Serializable
@Parcelize
data class SessionActivation(
    val employee: Employee,
    val service: ConfiguredService,
    val customerId: String,
    val note: String?,
) : Parcelable

@Serializable
@Parcelize
data class SessionUsage(
    val id: String,
    val data: SessionUsageData,
) : Parcelable

@Serializable
@Parcelize
data class SessionUsageData(
    val employee: Employee,
    val note: String?,
    val date: ZonedDateTime,
) : Parcelable

@Serializable
@Parcelize
data class SessionManualDeactivation(
    val id: String,
    val data: SessionManualDeactivationData,
) : Parcelable

@Serializable
@Parcelize
data class SessionManualDeactivationData(
    val note: String?,
    val reason: SessionManualDeactivationReason,
    val employee: Employee,
    val date: ZonedDateTime,
) : Parcelable


enum class SessionManualDeactivationReason {
    UNKNOWN
}