package com.bed.chat.presentation.shared.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

import com.bed.chat.domain.models.output.ChatOutputModel
import com.bed.chat.presentation.shared.preview.fake.chatOneFake
import com.bed.chat.presentation.shared.preview.fake.chatTwoFake
import com.bed.chat.presentation.shared.preview.fake.chatThreeFake
import com.bed.chat.presentation.shared.preview.fake.chatFourFake

class ChatPreviewParameterProvider : PreviewParameterProvider<ChatOutputModel> {
    override val values: Sequence<ChatOutputModel>
        get() = sequenceOf(
            chatOneFake,
            chatTwoFake,
            chatThreeFake,
            chatFourFake
        )
}

class ChatsPreviewParameterProvider : PreviewParameterProvider<List<ChatOutputModel>> {
    override val values: Sequence<List<ChatOutputModel>>
        get() = sequenceOf(
            listOf(
                chatOneFake,
                chatTwoFake,
                chatThreeFake,
                chatFourFake
            )
        )
}
