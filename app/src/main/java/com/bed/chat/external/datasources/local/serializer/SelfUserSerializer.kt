package com.bed.chat.external.datasources.local.serializer

import java.io.InputStream
import java.io.OutputStream

import androidx.datastore.core.Serializer
import androidx.datastore.core.CorruptionException

import com.bed.chat.SelfUser
import com.google.protobuf.InvalidProtocolBufferException

object SelfUserSerializer : Serializer<SelfUser> {
    override val defaultValue: SelfUser get() = SelfUser.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SelfUser = try {
        SelfUser.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
        throw CorruptionException("Cannot read proto.", exception)
    }

    override suspend fun writeTo(t: SelfUser, output: OutputStream) { t.writeTo(output) }
}
