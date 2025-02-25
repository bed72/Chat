package com.bed.chat.external.clients.response

import java.time.ZoneId
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Long.toTimestamp(): String {
    val now = LocalDateTime.now()
    val time = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )

    return if (now.toLocalDate() == time.toLocalDate()) time.format(DateTimeFormatter.ofPattern("HH:mm"))
    else time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}
