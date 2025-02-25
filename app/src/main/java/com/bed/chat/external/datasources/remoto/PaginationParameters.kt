package com.bed.chat.external.datasources.remoto

import io.ktor.http.path
import io.ktor.http.URLBuilder

import com.bed.chat.external.clients.HttpUrl
import com.bed.chat.external.clients.request.PaginationRequest

fun URLBuilder.configurePaginationParameter(path: HttpUrl, parameter: PaginationRequest, arguments: Any? = null) {
    path(if (arguments == null) path.value else "${path.value}/$arguments")
    parameters.append("limit", parameter.limit.toString())
    parameters.append("offset", parameter.offset.toString())
}
