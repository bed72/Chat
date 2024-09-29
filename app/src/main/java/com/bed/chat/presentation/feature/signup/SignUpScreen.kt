package com.bed.chat.presentation.feature.signup

import androidx.lifecycle.viewmodel.compose.viewModel

import android.content.res.Configuration.UI_MODE_NIGHT_YES

import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api

import com.bed.chat.R

import com.bed.chat.presentation.shared.theme.ChatTheme

import com.bed.chat.presentation.shared.components.Header
import com.bed.chat.presentation.shared.components.Container
import com.bed.chat.presentation.shared.components.PrimaryButton
import com.bed.chat.presentation.shared.components.TextLinkButton
import com.bed.chat.presentation.shared.components.PrimaryTextField

import com.bed.chat.presentation.feature.signup.state.SignUpFormState
import com.bed.chat.presentation.feature.signup.state.SignUpFormEvent

import com.bed.chat.presentation.shared.components.selector.PictureSelector
import com.bed.chat.presentation.shared.components.selector.PictureSelectorBottomSheet

@Composable
fun SignUpInitScreen(
    onNavigateToSignIn: () -> Unit,
    viewModel: SignUpViewModel = viewModel()
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

    val (openPictureSelectorBottomSheet, setOpenPictureSelectorBottomSheet) =
        remember { mutableStateOf(false) }


    Container {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,

        ) {

            Header(
                image = R.drawable.ic_launcher_foreground,
                imageDescription = R.string.app_icon_description,
                title = R.string.sign_up_title,
                subtitle = R.string.sign_up_sub_title
            )

            Spacer(modifier = modifier.height(16.dp))

            PictureSelector(
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { setOpenPictureSelectorBottomSheet(true) }
            )

            Spacer(modifier = modifier.height(16.dp))

            PrimaryTextField(
                value = "",
                label = stringResource(id = R.string.label_first_name_input),
                placeholder = stringResource(id = R.string.placeholder_first_name_input),
                onValueChange = {  }
            )

            Spacer(modifier = modifier.height(16.dp))

            PrimaryTextField(
                value = "",
                label = stringResource(id = R.string.label_second_name_input),
                placeholder = stringResource(id = R.string.placeholder_second_name_input),
                onValueChange = {  }
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
                onValueChange = { onFormEvent(SignUpFormEvent.PasswordChanged(it)) }
            )

            Spacer(modifier = modifier.height(16.dp))

            PrimaryTextField(
                value = "",
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                label = stringResource(id = R.string.label_confirm_password_input),
                placeholder = stringResource(id = R.string.placeholder_confirm_password_input),
                onValueChange = {  }
            )

            Spacer(modifier = modifier.height(32.dp))

            PrimaryButton(
                isLoading = false,
                text = stringResource(id = R.string.sign_up_title_button),
                onClick = { onFormEvent(SignUpFormEvent.Submit) }
            )

            Spacer(modifier = modifier.height(32.dp))

            TextLinkButton(
                text = R.string.sign_up_description_create_account,
                link = R.string.sign_up_description_create_account_link,
                click = { onNavigateToSignIn() }
            )

            if (openPictureSelectorBottomSheet)
                PictureSelectorBottomSheet(
                    onDismissRequest = { setOpenPictureSelectorBottomSheet(false) }
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
