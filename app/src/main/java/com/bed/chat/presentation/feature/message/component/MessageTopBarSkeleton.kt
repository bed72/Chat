package com.bed.chat.presentation.feature.message.component

import com.valentinilk.shimmer.shimmer

import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ConstraintLayout

import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
fun MessageTopBarSkeleton(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shimmer()
    ) {

        val (
            avatarRef,
            firstNameRef,
            lastMessageRef,

        ) = createRefs()

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(42.dp)
                .background(Color.Gray)
                .constrainAs(avatarRef) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                },
        )

        Box(
            modifier = Modifier
                .height(16.dp)
                .background(Color.Gray)
                .constrainAs(firstNameRef) {
                    top.linkTo(avatarRef.top)
                    start.linkTo(avatarRef.end, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    bottom.linkTo(lastMessageRef.top)
                    width = Dimension.fillToConstraints
                },
        )

        Box(
            modifier = Modifier
                .height(16.dp)
                .background(Color.Gray)
                .constrainAs(lastMessageRef) {
                    top.linkTo(firstNameRef.bottom)
                    start.linkTo(avatarRef.end, margin = 16.dp)
                    end.linkTo(parent.end, margin = 128.dp)
                    bottom.linkTo(avatarRef.bottom)
                    width = Dimension.fillToConstraints
                },
        )

    }
}

@Preview
@Composable
private fun MessageTopBarSkeletonPreview() {
    ChatTheme {
        MessageTopBarSkeleton()
    }
}
