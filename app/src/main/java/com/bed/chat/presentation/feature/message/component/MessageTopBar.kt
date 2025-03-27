package com.bed.chat.presentation.feature.message.component

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import com.bed.chat.R

import com.bed.chat.domain.models.output.UserOutputModel

import com.bed.chat.presentation.feature.message.MessageViewModel

import com.bed.chat.presentation.shared.theme.ChatTheme
import com.bed.chat.presentation.shared.components.TopBar
import com.bed.chat.presentation.shared.components.RoundedAvatar
import com.bed.chat.presentation.shared.preview.fake.userOneFake
import com.bed.chat.presentation.shared.modifiers.noRippleClickable

@Composable
fun MessageTopBar(
    state: MessageViewModel.UserState,
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        MessageViewModel.UserState.Loading -> MessageTopBarSkeleton(modifier)
        is MessageViewModel.UserState.Failure -> HandleUser(null, goBack, modifier)
        is MessageViewModel.UserState.Success -> HandleUser(state.user, goBack, modifier)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HandleUser(
    user: UserOutputModel?,
    goBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopBar(
        modifier = modifier,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RoundedAvatar(
                    model = user?.profilePicture,
                    size = 42.dp,
                    iconColor = MaterialTheme.colorScheme.background,
                    backgroundColor = MaterialTheme.colorScheme.background
                )
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = user?.firstName ?: "...",
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = if (user == null) "..." else "Online",
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = MaterialTheme.colorScheme.background,
                contentDescription = stringResource(R.string.common_go_back_icon_description),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .noRippleClickable(goBack)
            )
        }
    )
}

@Preview
@Composable
private fun MessageTopBarPreview() {
    ChatTheme {
        MessageTopBar(
            state = MessageViewModel.UserState.Success(userOneFake),
            goBack = {}
        )
    }
}
