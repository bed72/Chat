package com.bed.chat.external.mappers

import javax.inject.Inject

import kotlinx.serialization.json.Json

import com.bed.chat.data.mappers.Mapper

import com.bed.chat.external.clients.fcm.models.NotificationModel

class NotificationMapper @Inject constructor() : Mapper<String, NotificationModel> {
    override operator fun invoke(parameter: String): NotificationModel =
        Json.decodeFromString(parameter)
}
