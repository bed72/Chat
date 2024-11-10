package com.bed.chat.domain.repositories

import com.bed.chat.domain.alias.DomainSignInType
import com.bed.chat.domain.alias.DomainSignUpType

import com.bed.chat.domain.models.input.SignInInputModel
import com.bed.chat.domain.models.input.SignUpInputModel

interface AuthenticationRepository {
    suspend fun signUp(parameter: SignUpInputModel): DomainSignUpType
    suspend fun signIn(parameter: SignInInputModel): DomainSignInType
}
