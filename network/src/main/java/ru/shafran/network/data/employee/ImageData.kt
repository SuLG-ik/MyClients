@file:UseSerializers(ZonedDateTimeSerializer::class)
package ru.shafran.network.data.employee

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import ru.shafran.network.utils.ZonedDateTimeSerializer
import java.time.ZonedDateTime

@Serializable
data class ImageData(
    val url: String,
    val date: ZonedDateTime,
)