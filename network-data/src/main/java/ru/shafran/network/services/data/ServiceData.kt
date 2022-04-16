package ru.shafran.network.services.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import ru.shafran.network.assets.data.AssetData
import ru.shafran.network.session.data.ServiceConfigurationReference

@Serializable
@Parcelize
data class Service(
    val id: String,
    val data: ServiceData,
) : Parcelable


@Parcelize
@Serializable
data class ServiceData(
    val info: ServiceInfo,
    val image: AssetData? = null,
    val configurations: List<ServiceConfiguration> = emptyList(),
) : Parcelable

@Parcelize
@Serializable
data class ConfiguredService(
    val serviceId: String,
    val info: ServiceInfo,
    val image: AssetData? = null,
    val configuration: ServiceConfiguration,
) : Parcelable


@Parcelize
@Serializable
data class ServiceInfo(
    val title: String,
    val description: String,
    val priority: Int,
    val isPublic: Boolean,
) : Parcelable


@Parcelize
@Serializable
data class ServiceConfiguration(
    val title: String,
    val description: String,
    val cost: Long,
    val amount: Long,
    val id: String,
) : Parcelable

@Parcelize
@Serializable
data class EditConfigurationRequest(
    val data: EditableServiceConfigurationData,
) : Parcelable

@Parcelize
@Serializable
data class EditableServiceConfigurationData(
    val title: String,
    val description: String,
    val cost: Long,
    val reference: ServiceConfigurationReference,
) : Parcelable