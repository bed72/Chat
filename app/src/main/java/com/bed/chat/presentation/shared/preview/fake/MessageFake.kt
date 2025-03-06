package com.bed.chat.presentation.shared.preview.fake

import com.bed.chat.domain.models.output.MessageOutputModel

val messageOneFake = MessageOutputModel(
    id = 1,
    autoId = 1,
    senderId = 1,
    receiverId = 2,
    message = "Olá",
    timestamp = "15:00",
    isSelf = true,
    isUnread = true,
)

val messageTwoFake = MessageOutputModel(
    id = 2,
    autoId = 2,
    senderId = 2,
    receiverId = 1,
    message = "Oi, tudo bem?",
    timestamp = "15:05",
    isSelf = false,
    isUnread = true,
)

val messageThreeFake = MessageOutputModel(
    id = 3,
    autoId = 3,
    senderId = 1,
    receiverId = 2,
    message = "Tudo bem sim, e com você?",
    timestamp = "15:07",
    isSelf = true,
    isUnread = false,
)

val messageFourFake = MessageOutputModel(
    id = 4,
    autoId = 4,
    senderId = 2,
    receiverId = 1,
    message = "Estou bem também, obrigado!",
    timestamp = "15:10",
    isSelf = false,
    isUnread = false,
)

val messageFiveFake = MessageOutputModel(
    id = 5,
    autoId = 5,
    senderId = 1,
    receiverId = 2,
    message = "Que bom! Vamos marcar de sair qualquer dia desses?",
    timestamp = "15:15",
    isSelf = true,
    isUnread = false,
)
