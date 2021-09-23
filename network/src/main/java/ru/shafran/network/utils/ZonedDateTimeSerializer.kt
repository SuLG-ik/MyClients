package ru.shafran.network.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime


internal object ZonedDateTimeSerializer : KSerializer<ZonedDateTime> {
    override fun deserialize(decoder: Decoder): ZonedDateTime {
        return decoder.decodeStructure(descriptor){
            val time = decodeLongElement(descriptor, 0)
            val zoneId = decodeStringElement(descriptor, 1)
            ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.of(zoneId))
        }
    }

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("date") {
            element<Long>("time")
            element<String>("zone_id")
        }

    override fun serialize(encoder: Encoder, value: ZonedDateTime) {
        encoder.encodeStructure(descriptor) {
            encodeLongElement(descriptor, 0, value.toInstant().toEpochMilli())
            encodeStringElement(descriptor, 1, value.zone.id)
        }
    }
}