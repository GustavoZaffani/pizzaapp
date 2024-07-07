package br.edu.utfpr.apppizzaria.data.network.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateSerializer : KSerializer<Date> {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        val string = dateFormat.format(value)
        when (encoder) {
            is JsonEncoder -> encoder.encodeString(string)
            else -> encoder.encodeString(string)
        }
    }

    override fun deserialize(decoder: Decoder): Date {
        val string = when (decoder) {
            is JsonDecoder -> decoder.decodeString()
            else -> decoder.decodeString()
        }
        return dateFormat.parse(string) ?: throw IllegalArgumentException("Invalid date format")
    }
}