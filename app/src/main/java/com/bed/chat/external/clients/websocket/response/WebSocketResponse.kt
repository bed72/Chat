package com.bed.chat.external.clients.websocket.response

import com.bed.chat.external.clients.http.response.MessageResponse

sealed interface WebSocketResponse {
    data object NotHandleYet : WebSocketResponse
    data class ConnectionFailure(val failure: Throwable) : WebSocketResponse
    data class MessageReceived(val message: MessageResponse) : WebSocketResponse
    data class ActiveUsersChanged(val ids: ActiveUserIdsResponse) : WebSocketResponse
}
