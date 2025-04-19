package com.bed.chat.external.clients.http

enum class HttpUrl(val value: String) {
    API("https://chat-api.androidmoderno.com.br"),
    WS("ws://chat-api.androidmoderno.com.br:8080/chat"),
    USERS("${API.value}/users"),
    SIGN_IN("${API.value}/signin"),
    SIGN_UP("${API.value}/signup"),
    MESSAGES("${API.value}/messages"),
    AUTHENTICATE("${API.value}/authenticate"),
    UPLOADING("${API.value}/profile-picture"),
    CONVERSATIONS("${API.value}/conversations"),
    NOTIFICATIONS("${API.value}/notifications/register")
}
