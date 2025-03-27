package com.bed.chat.external.clients.websocket.serializer

import kotlinx.serialization.serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.buildClassSerialDescriptor

import com.bed.chat.external.clients.http.response.MessageResponse

import com.bed.chat.external.clients.websocket.response.ActiveUserIdsResponse
import com.bed.chat.external.clients.websocket.response.WebSocketDataResponse

object WebSocketDataSerializer : KSerializer<WebSocketDataResponse> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor(serialName = "WebSocketDataResponse") {
            element("type", serialDescriptor<String>())
            element("data", buildClassSerialDescriptor("Any"))
        }

    override fun serialize(encoder: Encoder, value: WebSocketDataResponse) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.type)
            encodeSerializableElement(descriptor, 1, getDataSerializer(value.type), value.data)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): WebSocketDataResponse =
        decoder.decodeStructure(descriptor) {
            if (decodeSequentially()) {
                val type = decodeStringElement(descriptor, 0)
                val data = decodeSerializableElement(descriptor, 1, getDataSerializer(type))
                WebSocketDataResponse(type, data)
            } else {
                require(decodeElementIndex(descriptor) == 0) { "Type field should be precede data field" }
                val type = decodeStringElement(descriptor, 0)
                val data = when (val index = decodeElementIndex(descriptor)) {
                    1 -> decodeSerializableElement(descriptor, 1, getDataSerializer(type))
                    CompositeDecoder.DECODE_DONE -> throw SerializationException("Data is missing")
                    else -> error("Unexpected index: $index")
                }
                WebSocketDataResponse(type, data)
            }
        }

    @Suppress("UNCHECKED_CAST")
    private val typeSerializer: Map<String, KSerializer<Any>> = mapOf(
        "messageResponse" to serializer<MessageResponse>(),
        "activeUserIdsResponse" to serializer<ActiveUserIdsResponse>(),
    ).mapValues { (_, v) -> v as KSerializer<Any> }

    private fun getDataSerializer(type: String): KSerializer<Any> =
        typeSerializer[type] ?: throw SerializationException("Unknown type $type")
}
