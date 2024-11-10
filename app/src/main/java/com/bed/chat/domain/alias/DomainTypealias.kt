package com.bed.chat.domain.alias

import arrow.core.Either
import com.bed.chat.domain.models.output.FailureOutputModel

typealias DomainSignUpType = Either<FailureOutputModel, Unit>
typealias DomainSignInType = Either<FailureOutputModel, Unit>
