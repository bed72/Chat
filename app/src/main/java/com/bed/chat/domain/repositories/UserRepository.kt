package com.bed.chat.domain.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

import com.bed.chat.domain.models.output.user.UserOutputModel

interface UserRepository {
    suspend operator fun invoke(parameter: Int = 10): Flow<PagingData<UserOutputModel>>
}
