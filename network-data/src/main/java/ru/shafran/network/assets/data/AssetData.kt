@file:UseSerializers(ZonedDateTimeSerializer::class)

package ru.shafran.network.assets.data

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.shafran.network.utils.ZonedDateTimeSerializer
import java.time.ZonedDateTime

@Parcelize
@Serializable
data class AssetData(
    val url: String,
    val hash: String,
    val type: AssetType,
    val date: ZonedDateTime,
) : Parcelable