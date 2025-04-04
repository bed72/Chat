package com.bed.chat.domain.repositories

import com.bed.chat.domain.models.input.SignInInputModel
import com.bed.chat.domain.models.input.SignUpInputModel
import com.bed.chat.domain.models.output.ImageOutputModel
import com.bed.chat.domain.models.output.UserOutputModel
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    val currentUser: Flow<UserOutputModel>
    suspend fun authenticate(): Result<Unit>
    suspend fun signUp(parameter: SignUpInputModel): Result<Unit>
    suspend fun signIn(parameter: SignInInputModel): Result<Unit>
    suspend fun uploadProfilePicture(parameter: String): Result<ImageOutputModel>
}
