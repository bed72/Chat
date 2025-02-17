package com.bed.chat.data.repositories

import jakarta.inject.Inject

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

import androidx.paging.map
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingConfig

import com.bed.chat.external.paging.UserPagingSource
import com.bed.chat.external.clients.response.user.toModel

import com.bed.chat.domain.repositories.UserRepository
import com.bed.chat.domain.models.output.user.UserOutputModel
import com.bed.chat.external.clients.response.user.UserResponse

class UserRepositoryImpl @Inject constructor(
    private val paging: UserPagingSource
) : UserRepository {
    override fun invoke(parameter: Int): Flow<PagingData<UserOutputModel>> =
        Pager(
            config = buildConfig(parameter),
            pagingSourceFactory = { paging }
        ).flow.map { it.toModel() }

    private fun buildConfig(parameter: Int) = PagingConfig(
        pageSize = parameter,
        enablePlaceholders = false
    )

    private fun PagingData<UserResponse>.toModel() =
        map { response -> response.toModel(false) }
}
