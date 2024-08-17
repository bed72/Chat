package com.bed.chat.presentation.feature.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape

import com.bed.chat.R
import com.bed.chat.presentation.components.PrimaryTextField

@Composable
fun SignInInitScreen() {
    SignInScreen()
}

@Composable
fun SignInScreen(modifier: Modifier = Modifier) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.app_icon_description)
        )

        PrimaryTextField(
            value = email,
            onValueChange = setEmail,
            keyboardType = KeyboardType.Email,
            label = stringResource(id = R.string.label_email_input),
            placeholder = stringResource(id = R.string.placeholder_email_input)
        )

        Spacer(modifier = modifier.height(16.dp))

        PrimaryTextField(
            value = password,
            imeAction = ImeAction.Done,
            onValueChange = setPassword,
            keyboardType = KeyboardType.Password,
            label = stringResource(id = R.string.label_password_input),
            placeholder = stringResource(id = R.string.placeholder_password_input)
        )

        Spacer(modifier = modifier.height(32.dp))

        Button(
            modifier = modifier
                .height(48.dp)
                .fillMaxSize(),
            shape = RoundedCornerShape(8.dp),
            onClick = {  }
        ) {
            Text(text = "Entrar")
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun SplashScreenPreview() {
    SignInScreen()
}
