package com.bed.chat.data.datasources

import com.bed.chat.external.clients.http.request.TokenRequest

interface NotificationDatasource {
    suspend fun registerToken(parameter: TokenRequest): Result<Unit>
}
