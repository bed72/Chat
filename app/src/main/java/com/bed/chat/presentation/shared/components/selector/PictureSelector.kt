package com.bed.chat.presentation.shared.components.selector

import android.net.Uri

import coil.compose.AsyncImage

import androidx.compose.runtime.Composable

import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape

import com.bed.chat.R
import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
fun PictureSelector(
    modifier: Modifier = Modifier,
    picture: Uri? = null
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            model = picture ?: R.drawable.ic_user,
            modifier = Modifier.size(84.dp).clip(CircleShape),
            placeholder = painterResource(id = R.drawable.ic_user),
            contentDescription = stringResource(R.string.picture_selector_description),
            colorFilter = if (picture == null) ColorFilter.tint(MaterialTheme.colorScheme.onSurface) else null
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.picture_selector_title),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PictureSelectorPreview() {
    ChatTheme {
        PictureSelector()
    }
}
