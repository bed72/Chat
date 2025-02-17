package com.bed.chat.domain.models.output.user

data class PaginatedUserOutputModel(
    val total: Int,
    val hasMore: Boolean,
    val users: List<UserOutputModel>,
)
