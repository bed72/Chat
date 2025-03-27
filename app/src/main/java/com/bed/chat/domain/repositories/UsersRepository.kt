package com.bed.chat.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

import com.bed.chat.domain.models.output.UserOutputModel

interface UsersRepository {
    suspend fun getUser(parameter: Int): Result<UserOutputModel>
    fun getUsers(parameter: Int = 10): Flow<PagingData<UserOutputModel>>
}
