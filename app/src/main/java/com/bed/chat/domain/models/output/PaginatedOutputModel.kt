package com.bed.chat.domain.models.output

data class PaginatedOutputModel<out T>(
    val total: Int,
    val hasMore: Boolean,
    val data: List<T>,
)
