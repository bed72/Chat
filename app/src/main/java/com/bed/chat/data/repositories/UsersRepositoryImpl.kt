package com.bed.chat.data.repositories

import javax.inject.Inject

import androidx.paging.map
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingConfig

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.CoroutineDispatcher

import com.bed.chat.data.datasources.UsersDatasource

import com.bed.chat.domain.repositories.UsersRepository
import com.bed.chat.domain.models.output.UserOutputModel

import com.bed.chat.external.modules.IoDispatcher
import com.bed.chat.external.clients.http.response.toModel
import com.bed.chat.external.clients.paging.UserPagingSource
import com.bed.chat.external.clients.http.response.UserResponse

class UsersRepositoryImpl @Inject constructor(
    private val datasource: UsersDatasource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : UsersRepository {
    override suspend fun getUser(parameter: Int): Result<UserOutputModel> =
        safeCallResult(ioDispatcher){
            datasource.getUser(parameter).map { it.toModel(false) }.getOrThrow()
        }

    override fun getUsers(parameter: Int): Flow<PagingData<UserOutputModel>> {
        val pager = Pager(
            config = buildConfig(parameter),
            pagingSourceFactory = { UserPagingSource(datasource) }
        )
            .flow
            .flowOn(ioDispatcher)

        return pager.map { it.toModel() }
    }

    private fun buildConfig(parameter: Int) = PagingConfig(
        prefetchDistance = 1,
        pageSize = parameter,
        enablePlaceholders = false,
        initialLoadSize = parameter,
    )

    private fun PagingData<UserResponse>.toModel() =
        map { it.toModel(false) }
}
