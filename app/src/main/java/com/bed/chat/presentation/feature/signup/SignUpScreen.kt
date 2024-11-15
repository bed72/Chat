package com.bed.chat.presentation.feature.signup

import kotlinx.coroutines.launch

import androidx.hilt.navigation.compose.hiltViewModel

import android.content.res.Configuration.UI_MODE_NIGHT_YES

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

import com.bed.chat.R

import com.bed.chat.presentation.feature.signup.state.SignUpFormState
import com.bed.chat.presentation.feature.signup.state.SignUpFormEvent

import com.bed.chat.presentation.shared.theme.ChatTheme

import com.bed.chat.presentation.shared.components.Header
import com.bed.chat.presentation.shared.components.Container
import com.bed.chat.presentation.shared.components.PrimaryButton
import com.bed.chat.presentation.shared.components.TextLinkButton
import com.bed.chat.presentation.shared.components.PrimaryTextField
import com.bed.chat.presentation.shared.components.selector.PictureSelector
import com.bed.chat.presentation.shared.components.selector.PictureSelectorBottomSheet

@Composable
fun SignUpInitScreen(
    onNavigateToSignIn: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    SignUpScreen(
        formState = viewModel.formState,
        onFormEvent = viewModel::onFormEvent,
        onNavigateToSignIn = onNavigateToSignIn,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    formState: SignUpFormState,
    onNavigateToSignIn: () -> Unit,
    onFormEvent: (SignUpFormEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Container {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,

        ) {

            Header(
                title = R.string.sign_up_title,
                subtitle = R.string.sign_up_sub_title,
            )

            Spacer(modifier = modifier.height(32.dp))

            PictureSelector(
                picture = formState.picture,
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onFormEvent(SignUpFormEvent.OpenPictureSelectorBottomSheet) }
            )

            Spacer(modifier = modifier.height(16.dp))

            PrimaryTextField(
                value = formState.name,
                label = stringResource(id = R.string.label_first_name_input),
                message = formState.nameMessage,
                placeholder = stringResource(id = R.string.placeholder_first_name_input),
                onValueChange = { onFormEvent(SignUpFormEvent.NameChanged(it)) }
            )

            Spacer(modifier = modifier.height(16.dp))

            PrimaryTextField(
                value = formState.email,
                message = formState.emailMessage,
                keyboardType = KeyboardType.Email,
                label = stringResource(id = R.string.label_email_input),
                placeholder = stringResource(id = R.string.placeholder_email_input),
                onValueChange = { onFormEvent(SignUpFormEvent.EmailChanged(it)) }
            )

            Spacer(modifier = modifier.height(16.dp))

            PrimaryTextField(
                value = formState.password,
                message = formState.passwordMessage,
                keyboardType = KeyboardType.Password,
                label = stringResource(id = R.string.label_password_input),
                placeholder = stringResource(id = R.string.placeholder_password_input),
                onValueChange = { onFormEvent(SignUpFormEvent.PasswordChanged(it)) },
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    onFormEvent(SignUpFormEvent.Submit)
                })
            )

            Spacer(modifier = modifier.height(16.dp))

            Spacer(modifier = modifier.height(32.dp))

            PrimaryButton(
                isLoading = false,
                text = stringResource(id = R.string.sign_up_title_button),
                onClick = {
                    keyboardController?.hide()
                    onFormEvent(SignUpFormEvent.Submit)
                }
            )

            Spacer(modifier = modifier.height(32.dp))

            TextLinkButton(
                link = R.string.sign_up_description_create_account_link,
                text = R.string.sign_up_description_create_account,
                click = {
                    onNavigateToSignIn()
                    keyboardController?.hide()
                }
            )

            if (formState.isPictureSelectorBottomSheetOpen)
                PictureSelectorBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        onFormEvent(SignUpFormEvent.ClosePictureSelectorBottomSheet)
                    },
                    onPictureSelected = { uri ->
                        onFormEvent(SignUpFormEvent.PictureChanged(uri))

                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible)
                                onFormEvent(SignUpFormEvent.ClosePictureSelectorBottomSheet)
                        }
                    }
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
private fun SignUpScreenPreview() {
    ChatTheme {
        SignUpScreen(
            onFormEvent = {},
            onNavigateToSignIn = {},
            formState = SignUpFormState()
        )
    }
}
