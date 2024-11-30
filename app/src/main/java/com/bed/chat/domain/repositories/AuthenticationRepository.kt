package com.bed.chat.domain.repositories

import com.bed.chat.domain.models.input.SignInInputModel
import com.bed.chat.domain.models.input.SignUpInputModel

interface AuthenticationRepository {
    suspend fun signUp(parameter: SignUpInputModel): Result<Unit>
    suspend fun signIn(parameter: SignInInputModel): Result<Unit>
}
