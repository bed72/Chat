package com.bed.chat.presentation.shared.components

import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.constraintlayout.compose.ConstraintLayout

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter

import com.bed.chat.domain.models.output.ChatOutputModel
import com.bed.chat.presentation.shared.modifiers.noRippleClickable

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.preview.provider.ChatPreviewParameterProvider

@Composable
fun ChatItem(
    model: ChatOutputModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val receiver = remember (model.members) {
        model.otherMember
    }

    ConstraintLayout(
        modifier = modifier
            .noRippleClickable(onClick)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val (
            avatarRef,
            firstNameRef,
            lastMessageRef,
            unreadCountRef,
            lastMessageTimeRef,
        ) = createRefs()

        RoundedAvatar(
            receiver.profilePicture,
            modifier = Modifier
                .constrainAs(avatarRef) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                },
        )

        Text(
            text = receiver.firstName,
            modifier = Modifier
                .constrainAs(firstNameRef) {
                    top.linkTo(avatarRef.top)
                    start.linkTo(avatarRef.end, margin = 16.dp)
                    end.linkTo(lastMessageTimeRef.start, margin = 16.dp)
                    bottom.linkTo(lastMessageRef.top)
                    width = Dimension.fillToConstraints
                },
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = model.lastMessage ?: "",
            modifier = Modifier
                .constrainAs(lastMessageRef) {
                    top.linkTo(firstNameRef.bottom)
                    start.linkTo(avatarRef.end, margin = 16.dp)
                    end.linkTo(unreadCountRef.start, margin = 16.dp)
                    bottom.linkTo(avatarRef.bottom)
                    width = Dimension.fillToConstraints
                },
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            text = model.timestamp,
            modifier = Modifier
                .constrainAs(lastMessageTimeRef) {
                    top.linkTo(firstNameRef.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(firstNameRef.bottom)
                    width = Dimension.wrapContent
                },
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodySmall,
        )

        Text(
            text = model.unreadCount.toString(),
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 4.dp)
                .constrainAs(unreadCountRef) {
                    top.linkTo(lastMessageTimeRef.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(lastMessageRef.bottom)
                    width = Dimension.wrapContent
                    visibility = if (model.unreadCount > 0) Visibility.Visible
                    else Visibility.Gone
                },
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Preview
@Composable
private fun ChatItemPreview(
    @PreviewParameter(ChatPreviewParameterProvider::class) chat: ChatOutputModel
) {
    ChatTheme {
        ChatItem(
            model = chat,
            onClick = {}
        )
    }
}
