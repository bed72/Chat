package com.bed.chat.presentation.components

import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.PasswordVisualTransformation

import com.bed.chat.R

@Composable
fun PrimaryTextField(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    label: String? = null,
    message: String? = null,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    val (isFocused, setIsFocused) = remember { mutableStateOf(false) }
    val (iconIsVisible, setIconIsVisible) = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        LabelTextField(modifier = modifier, label = label)

        OutlinedTextField(
            value = value,
            singleLine = true,
            isError = message != null,
            onValueChange = onValueChange,
            shape = MaterialTheme.shapes.medium,
            visualTransformation = handleVisualTransformation(iconIsVisible, keyboardType),
            keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
            trailingIcon = {
                IconTextField(
                    modifier,
                    value.isNotEmpty(),
                    iconIsVisible,
                    keyboardType,
                    setIconIsVisible
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.isFocused) setIsFocused(true) else setIsFocused(false)
                },
            placeholder = {
                if (isFocused)
                    Text(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.outline,
                        style = MaterialTheme.typography.bodyLarge
                    )
            },
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = MaterialTheme.colorScheme.error,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        MessageTextField(modifier = modifier, message = message)
    }
}

@Composable
private fun LabelTextField(modifier: Modifier, label: String?) {
    label?.let {
        Text(
            text = it,
            modifier = modifier.padding(start = 4.dp),
            color = MaterialTheme.colorScheme.outline,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = modifier.height(4.dp))
    }
}

@Composable
private fun MessageTextField(modifier: Modifier, message: String?) {
    message?.let {
        Spacer(modifier = modifier.height(4.dp))
        Text(
            text = it,
            modifier = modifier.padding(start = 4.dp),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun IconTextField(
    modifier: Modifier,
    mustShow: Boolean,
    iconIsVisible: Boolean,
    keyboardType: KeyboardType,
    setIconIsVisible: (Boolean) -> Unit
) {
    if (mustShow && keyboardType == KeyboardType.Password)
        Icon(
            handleIcon(iconIsVisible),
            tint = MaterialTheme.colorScheme.outline,
            modifier = modifier.clickable { setIconIsVisible(!iconIsVisible) },
            contentDescription = stringResource(handleIconDescription(iconIsVisible))
        )
    else Spacer(modifier.size(0.dp))
}

private fun handleIconDescription(iconIsVisible: Boolean) =
    if (iconIsVisible) R.string.hide_password_input else R.string.show_password_input

private fun handleIcon(iconIsVisible: Boolean) =
    if (iconIsVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility

private fun handleVisualTransformation(iconIsVisible: Boolean, keyboardType: KeyboardType) =
    if (keyboardType == KeyboardType.Password && !iconIsVisible) PasswordVisualTransformation()
    else VisualTransformation.None

@Preview
@Composable
private fun PrimaryTextFieldEmailValidPreview() {
    PrimaryTextField(
        value = "",
        label = "E-mail",
        placeholder = "Digite seu e-mail",
        onValueChange = {}
    )
}
