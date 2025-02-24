package com.bed.chat.external.paging

import javax.inject.Inject

import androidx.paging.PagingState
import androidx.paging.PagingSource

import com.bed.chat.data.datasources.UserDatasource
import com.bed.chat.external.clients.request.PaginationRequest
import com.bed.chat.external.clients.response.user.UserResponse

class UserPagingSource @Inject constructor(
    private val datasource: UserDatasource
) : PagingSource<Int, UserResponse>() {
    override fun getRefreshKey(state: PagingState<Int, UserResponse>): Int? =
        state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.run {
                prevKey?.plus(state.config.pageSize) ?: nextKey?.minus(10)
            }
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponse> = try {
        val limit = params.loadSize
        val offset = params.key ?: 0
        val response = getUsers(limit, offset).getOrThrow()

        LoadResult.Page(
            prevKey = null,
            data = response.users,
            nextKey = if (!response.hasMore) null else limit + offset
        )
    } catch (exception: Exception) {
        LoadResult.Error(exception)
    }

    private suspend fun getUsers(limit: Int, offset: Int) = datasource(
        PaginationRequest(limit, offset)
    )
}
