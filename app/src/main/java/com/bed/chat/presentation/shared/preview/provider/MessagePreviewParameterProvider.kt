package com.bed.chat.presentation.shared.preview.provider

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

import com.bed.chat.domain.models.MessageOutputModel

import com.bed.chat.presentation.shared.preview.fake.messageOneFake
import com.bed.chat.presentation.shared.preview.fake.messageTwoFake
import com.bed.chat.presentation.shared.preview.fake.messageThreeFake
import com.bed.chat.presentation.shared.preview.fake.messageFourFake
import com.bed.chat.presentation.shared.preview.fake.messageFiveFake

class MessagePreviewParameterProvider : PreviewParameterProvider<Flow<PagingData<MessageOutputModel>>> {
    override val values: Sequence<Flow<PagingData<MessageOutputModel>>>
        get() = sequenceOf(
            flowOf(
                PagingData.from(
                    listOf(
                        messageOneFake,
                        messageTwoFake,
                        messageThreeFake,
                        messageFourFake,
                        messageFiveFake
                    ),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.NotLoading(true),
                        prepend = LoadState.NotLoading(false),
                        append = LoadState.NotLoading(false)
                    )
                )
            )
        )
}
