package com.bed.chat.external.datasources.remoto

import okhttp3.HttpUrl.Companion.toHttpUrl

import com.bed.chat.external.clients.http.HttpUrl
import com.bed.chat.external.clients.http.request.PaginationRequest

fun configurePaginationParameter(path: HttpUrl, parameter: PaginationRequest, arguments: Any? = null): String {
    val base = if (arguments == null) path.value else "${path.value}/$arguments"

    return base.toHttpUrl().newBuilder()
        .apply {
            addQueryParameter("limit", parameter.limit.toString())
            addQueryParameter("offset", parameter.offset.toString())
        }
        .build()
        .toString()
}
