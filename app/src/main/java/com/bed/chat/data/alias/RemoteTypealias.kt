package com.bed.chat.data.alias

import arrow.core.Either

import com.bed.chat.external.clients.response.TokenResponse

typealias RemoteSignUpType = Either<String, Unit>
typealias RemoteSignInType = Either<String, TokenResponse>

