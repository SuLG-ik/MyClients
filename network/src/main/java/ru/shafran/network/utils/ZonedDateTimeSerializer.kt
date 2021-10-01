package ru.shafran.network.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime


internal object ZonedDateTimeSerializer : KSerializer<ZonedDateTime> {
    override fun deserialize(decoder: Decoder): ZonedDateTime {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(decoder.decodeLong()),
            ZoneId.systemDefault())
//        decoder.decodeStructure(descriptor) {
//            val time = decodeLongElement(descriptor, 0)
//            val zoneId = decodeStringElement(descriptor, 1)
//            ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.of(zoneId))
//        }
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(ZonedDateTime::class.java.name, PrimitiveKind.LONG)
//            buildClassSerialDescriptor(ZonedDateTime::class.java.name) {
//            element<Long>("time")
//            element<String>("zone_id")
//        }

    override fun serialize(encoder: Encoder, value: ZonedDateTime) {
        encoder.encodeLong(value.toInstant().toEpochMilli())
//        encoder.encodeStructure(descriptor) {
//            encodeLongElement(descriptor, 0, value.toInstant().toEpochMilli())
//            encodeStringElement(descriptor, 1, value.zone.id)
//        }
    }
}