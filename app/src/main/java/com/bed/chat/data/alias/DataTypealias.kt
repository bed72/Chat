package com.bed.chat.data.alias

import arrow.core.Either
import com.bed.chat.external.clients.response.FailureResponse

import com.bed.chat.external.clients.response.TokenResponse

typealias DataSignUpType = Either<FailureResponse, Unit>
typealias DataSignInType = Either<FailureResponse, TokenResponse>

