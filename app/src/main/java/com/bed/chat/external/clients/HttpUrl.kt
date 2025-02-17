package com.bed.chat.external.clients

enum class HttpUrl(val value: String) {
    API("https://chat-api.androidmoderno.com.br"),
    USERS("/users"),
    SIGN_IN("/signin"),
    SIGN_UP("/signup"),
    AUTHENTICATE("/authenticate"),
    UPLOADING("/profile-picture"),
    CONVERSATIONS("/conversations"),
}
