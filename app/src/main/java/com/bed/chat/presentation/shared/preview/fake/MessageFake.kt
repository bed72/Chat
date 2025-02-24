package com.bed.chat.presentation.shared.preview.fake

import com.bed.chat.domain.models.MessageOutputModel

val messageOneFake = MessageOutputModel(
    id = 1,
    autoId = 1,
    senderId = 1,
    receiverId = 2,
    text = "Olá",
    date = "15:00",
    isSelf = true,
    isUnread = true,
)

val messageTwoFake = MessageOutputModel(
    id = 2,
    autoId = 2,
    senderId = 2,
    receiverId = 1,
    text = "Oi, tudo bem?",
    date = "15:05",
    isSelf = false,
    isUnread = true,
)

val messageThreeFake = MessageOutputModel(
    id = 3,
    autoId = 3,
    senderId = 1,
    receiverId = 2,
    text = "Tudo bem sim, e com você?",
    date = "15:07",
    isSelf = true,
    isUnread = false,
)

val messageFourFake = MessageOutputModel(
    id = 4,
    autoId = 4,
    senderId = 2,
    receiverId = 1,
    text = "Estou bem também, obrigado!",
    date = "15:10",
    isSelf = false,
    isUnread = false,
)

val messageFiveFake = MessageOutputModel(
    id = 5,
    autoId = 5,
    senderId = 1,
    receiverId = 2,
    text = "Que bom! Vamos marcar de sair qualquer dia desses?",
    date = "15:15",
    isSelf = true,
    isUnread = false,
)
