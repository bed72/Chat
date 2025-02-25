package com.bed.chat.presentation.shared.preview.fake

import com.bed.chat.domain.models.output.UserOutputModel

val userOneFake =  UserOutputModel(
    id = 0,
    self = true,
    username = "Gabriel",
    lastName = "Ramos",
    firstName = "Bed",
    profilePicture = ""
)

val userTwoFake =   UserOutputModel(
    id = 1,
    self = false,
    username = "Sha",
    lastName = "Kira",
    firstName = "Kirudinha",
    profilePicture = ""
)

val userThreeFake =   UserOutputModel(
    id = 2,
    self = false,
    username = "Sirius",
    lastName = "Black",
    firstName = "Blaquinho",
    profilePicture = ""
)

val userFourFake =   UserOutputModel(
    id = 3,
    self = false,
    username = "Elle",
    lastName = "L",
    firstName = "...",
    profilePicture = ""
)
