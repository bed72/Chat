package com.bed.chat.external.clients

enum class HttpUrl(val value: String) {
    API("https://chat-api.androidmoderno.com.br"),
    SIGN_IN("/signin"),
    SIGN_UP("/signup"),
    CHATS("/chats"),
    UPLOADING("/profile-picture"),
    VALIDATE_TOKEN("/authenticate"),
}
