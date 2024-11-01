package com.bed.chat.presentation.feature.signin

import androidx.lifecycle.viewmodel.compose.viewModel
import android.content.res.Configuration.UI_MODE_NIGHT_YES

import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState

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
fun SignInInitScreen(
    onNavigateToSignUp: () -> Unit,
    viewModel: SignInViewModel = viewModel {
        SignInViewModel(SignInFormValidator())
    }
) {
    SignInScreen(
        formState = viewModel.formState,
        onFormEvent = viewModel::onFormEvent,
        onNavigateToSignUp = onNavigateToSignUp
    )
}

@Composable
fun SignInScreen(
    formState: SignInFormState,
    onNavigateToSignUp: () -> Unit,
    onFormEvent: (SignInFormEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Container {
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
                value = formState.email,
                message = formState.emailMessage,
                keyboardType = KeyboardType.Email,
                label = stringResource(id = R.string.label_email_input),
                placeholder = stringResource(id = R.string.placeholder_email_input),
                onValueChange = { onFormEvent(SignInFormEvent.EmailChanged(it)) }
            )

            Spacer(modifier = modifier.height(16.dp))

            PrimaryTextField(
                value = formState.password,
                imeAction = ImeAction.Done,
                message = formState.passwordMessage,
                keyboardType = KeyboardType.Password,
                label = stringResource(id = R.string.label_password_input),
                placeholder = stringResource(id = R.string.placeholder_password_input),
                onValueChange = { onFormEvent(SignInFormEvent.PasswordChanged(it)) }
            )

            Spacer(modifier = modifier.height(32.dp))

            PrimaryButton(
                isLoading = formState.isLoading,
                text = stringResource(id = R.string.sign_in_title_button),
                onClick = { onFormEvent(SignInFormEvent.Submit) }
            )

            Spacer(modifier = modifier.height(32.dp))

            TextLinkButton(
                text = R.string.sign_in_description_create_account,
                link = R.string.sign_in_description_create_account_link,
                click = { onNavigateToSignUp() }
            )
        }
    }
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
private fun SignInScreenPreview() {
    ChatTheme {
        SignInScreen(
            onFormEvent = {},
            onNavigateToSignUp = {},
            formState = SignInFormState()
        )
    }
}
