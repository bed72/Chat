package com.bed.chat.presentation.shared.components.selector

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.annotation.DrawableRes

import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Density
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.defaultMinSize

import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState

import com.bed.chat.R
import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PictureSelectorBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onPictureSelected: (Uri) -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState()
) {

    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { it?.let(onPictureSelected) }
    )

    ModalBottomSheet(
        sheetState = sheetState,
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.surface,

    ) {

        PictureSelectorTitle()

        Spacer(modifier = Modifier.height(16.dp))

        PictureSelectorOption(
            icon = R.drawable.ic_camera,
            title = R.string.picture_selector_bottom_sheet_camera_title,
            iconDescription = R.string.picture_selector_bottom_sheet_camera_description,
            onClick = {}
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp))

        PictureSelectorOption(
            icon = R.drawable.ic_gallery,
            onClick = { picker.launch("image/*") },
            title = R.string.picture_selector_bottom_sheet_gallery_title,
            iconDescription = R.string.picture_selector_bottom_sheet_gallery_description
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
private fun PictureSelectorTitle() {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = stringResource(id = R.string.picture_selector_bottom_sheet_title),
        style = MaterialTheme
            .typography
            .labelLarge.copy(fontSize = 22.sp, fontWeight = FontWeight.W500)
    )
}

@Composable
private fun PictureSelectorOption(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    @StringRes iconDescription: Int,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = iconDescription)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
private fun PictureBottomSheetSelectorPreview() {
    val sheetState = SheetState(
        skipPartiallyExpanded = false,
        initialValue = SheetValue.Expanded,
        density = Density(LocalContext.current),
    )

    ChatTheme {
        PictureSelectorBottomSheet(
            onDismissRequest = {},
            onPictureSelected = {},
            sheetState = sheetState
        )
    }
}
