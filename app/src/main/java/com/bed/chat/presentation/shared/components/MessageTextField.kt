package com.bed.chat.presentation.shared.components

import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.VisualTransformation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.interaction.MutableInteractionSource

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme

@Composable
fun MessageTextField(
    message: String,
    onSendMessage: () -> Unit,
    onMessageChange: (text: String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier,
) {
    var value by remember { mutableStateOf(message) }

    BasicTextField(
        value = message,
        onValueChange = {
            value = it
            onMessageChange(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = ShapeDefaults.ExtraLarge.copy(CornerSize(12.dp))
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = ShapeDefaults.ExtraLarge.copy(CornerSize(12.dp))
            ),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
        decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp, max = 120.dp)
                        .padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = value,
                        placeholder = placeholder,
                        innerTextField = innerTextField,
                    )

                    Diver()

                    Spacer(modifier = Modifier.width(16.dp))

                    Button { onSendMessage() }
                }
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RowScope.TextField(
    value: String,
    placeholder: String,
    innerTextField: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth().weight(1f)
    ) {
        TextFieldDefaults.DecorationBox(
            value = value,
            enabled = false,
            singleLine = false,
            innerTextField = innerTextField,
            placeholder = { Placeholder(placeholder) },
            visualTransformation = VisualTransformation.None,
            interactionSource = remember { MutableInteractionSource() },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
            )
        )
    }
}

@Composable
private fun Placeholder(text: String) {
    Text(
        text = text,
        fontStyle = FontStyle.Italic,
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
private fun Diver() {
    Box(
        modifier = Modifier
            .height(32.dp)
            .width(1.dp)
            .background(color = MaterialTheme.colorScheme.outline,)
    )
}

@Composable
fun Button(onSendMessage: () -> Unit) {
    Surface(
        modifier = Modifier
            .size(32.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onSendMessage() },
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Icon(
            modifier = Modifier.padding(8.dp),
            imageVector = Icons.AutoMirrored.Default.Send,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            contentDescription = stringResource(R.string.message_text_field_button_description)
        )
    }
}

@Preview
@Composable
fun MessageTextFieldPreview() {
    ChatTheme {
        MessageTextField(
            message = "",
            onMessageChange = {},
            onSendMessage = {},
            placeholder = "Type a message",
        )
    }
}
