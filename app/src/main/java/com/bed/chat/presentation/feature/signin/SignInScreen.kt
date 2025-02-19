package com.bed.chat.presentation.feature.signin

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme

import com.bed.chat.presentation.shared.components.Header
import com.bed.chat.presentation.shared.components.Container
import com.bed.chat.presentation.shared.components.PrimaryButton
import com.bed.chat.presentation.shared.components.TextLinkButton
import com.bed.chat.presentation.shared.components.PrimaryTextField

import com.bed.chat.presentation.feature.signin.state.SignInFormEvent
import com.bed.chat.presentation.feature.signin.state.SignInFormState

@Composable
fun SignInRoute(
    onNavigateToChats: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    SignInScreen(
        formState = viewModel.formState,
        onFormEvent = viewModel::onFormEvent,
        onNavigateToChats = onNavigateToChats,
        onNavigateToSignUp = onNavigateToSignUp
    )
}

@Composable
fun SignInScreen(
    formState: SignInFormState,
    onNavigateToChats: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onFormEvent: (SignInFormEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val hostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = formState.messageSuccess) {
        formState.messageSuccess?.let { onNavigateToChats() }
    }

    LaunchedEffect(key1 = formState.messageFailure) {
        formState.messageFailure?.let {
            hostState.showSnackbar(it, duration = SnackbarDuration.Short)
        }
    }

    Container(
        content = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),

                verticalArrangement = Arrangement.Center,
            ) {
                Header(
                    title = R.string.sign_in_title,
                    subtitle = R.string.sign_in_sub_title
                )

                Spacer(modifier = modifier.height(32.dp))

                PrimaryTextField(
                    value = formState.username,
                    keyboardType = KeyboardType.Text,
                    message = formState.usernameMessage,
                    label = stringResource(id = R.string.label_first_name_input),
                    placeholder = stringResource(id = R.string.placeholder_first_name_input),
                    onValueChange = { onFormEvent(SignInFormEvent.UsernameChanged(it)) }
                )

                Spacer(modifier = modifier.height(16.dp))

                PrimaryTextField(
                    value = formState.password,
                    imeAction = ImeAction.Done,
                    message = formState.passwordMessage,
                    keyboardType = KeyboardType.Password,
                    label = stringResource(id = R.string.label_password_input),
                    onValueChange = { onFormEvent(SignInFormEvent.PasswordChanged(it)) },
                    placeholder = stringResource(id = R.string.placeholder_password_input),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        onFormEvent(SignInFormEvent.Submit)
                    })
                )

                Spacer(modifier = modifier.height(32.dp))

                PrimaryButton(
                    isLoading = formState.isLoading,
                    text = R.string.sign_in_title_button,
                    onClick = {
                        keyboardController?.hide()
                        onFormEvent(SignInFormEvent.Submit)
                    }
                )

                Spacer(modifier = modifier.height(32.dp))

                TextLinkButton(
                    text = R.string.sign_in_description_create_account,
                    link = R.string.sign_in_description_create_account_link,
                    click = {
                        onNavigateToSignUp()
                        keyboardController?.hide()
                    }
                )
            }
        },
        snackbar = { SnackbarHost(hostState = hostState) },
    )
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
)
private fun SignInScreenPreview() {
    ChatTheme {
        SignInScreen(
            onFormEvent = {},
            onNavigateToChats = {},
            onNavigateToSignUp = {},
            formState = SignInFormState()
        )
    }
}
