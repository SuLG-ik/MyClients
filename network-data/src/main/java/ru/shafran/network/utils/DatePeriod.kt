@file:UseSerializers(LocalDateSerializer::class)

package ru.shafran.network.utils

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDate.ofEpochDay
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.zone.ZoneRules
import java.util.*


@Serializable
@Parcelize
data class DatePeriod(
    val from: LocalDate,
    val to: LocalDate,
): Parcelable

fun Date.toLocalDate(): LocalDate {
    return LocalDate(toInstant(), ZoneId.systemDefault())
}

const val HOURS_PER_DAY = 24

const val MINUTES_PER_HOUR = 60

const val SECONDS_PER_MINUTE = 60

const val SECONDS_PER_HOUR: Int =
    SECONDS_PER_MINUTE * MINUTES_PER_HOUR

const val SECONDS_PER_DAY: Int =
    SECONDS_PER_HOUR * HOURS_PER_DAY

private fun LocalDate(instant: Instant, zone: ZoneId): LocalDate {
    val rules: ZoneRules = zone.rules
    val offset: ZoneOffset = rules.getOffset(instant)
    val localSecond = instant.epochSecond + offset.totalSeconds
    val localEpochDay = localSecond.floorDiv(SECONDS_PER_DAY)
    return ofEpochDay(localEpochDay)
}

fun LocalDate.toStartOfDate(): Date {
    return Date.from(atStartOfDay().toInstant(ZoneOffset.UTC))
}

object LocalDateSerializer : KSerializer<LocalDate> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.LONG)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeLong(value.toEpochDay())
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val days = decoder.decodeLong()
        return LocalDate.ofEpochDay(days)
    }

}
