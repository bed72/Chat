package com.bed.chat.presentation.shared.preview.fake

import com.bed.chat.domain.models.output.chat.ChatOutputModel

val chatOneFake = ChatOutputModel(
    id = 0,
    unreadCount = 7,
    timestamp =  "12:27",
    lastMessage =  "Quero Café!!!",
    listOf(
        userOneFake,
        userTwoFake
    )
)

val chatTwoFake = ChatOutputModel(
    id = 1,
    unreadCount = 2,
    timestamp =  "27/02/2027",
    lastMessage =  "Quero mais Café!!!",
    listOf(
        userTwoFake,
        userThreeFake
    )
)

val chatThreeFake = ChatOutputModel(
    id = 2,
    unreadCount = 4,
    timestamp =  "00:00",
    lastMessage =  "Acabou o Café :(",
    listOf(
        userThreeFake,
        userFourFake
    )
)

val chatFourFake = ChatOutputModel(
    id = 3,
    unreadCount = 8,
    timestamp =  "00:00",
    lastMessage =  "Vou comprar mais Café :)",
    listOf(
        userOneFake,
        userTwoFake,
        userThreeFake,
        userFourFake
    )
)
