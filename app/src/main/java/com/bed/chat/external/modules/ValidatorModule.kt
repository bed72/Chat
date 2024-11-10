package com.bed.chat.external.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

import com.bed.chat.presentation.shared.validator.FormValidator

import com.bed.chat.presentation.feature.signup.SignUpFormValidator
import com.bed.chat.presentation.feature.signup.state.SignUpFormState

import com.bed.chat.presentation.feature.signin.SignInFormValidator
import com.bed.chat.presentation.feature.signin.state.SignInFormState

@Module
@InstallIn(ViewModelComponent::class)
interface ValidatorModule {
    @Binds
    fun bindSignUpValidator(validator: SignUpFormValidator): FormValidator<SignUpFormState>

    @Binds
    fun bindSignInValidator(validator: SignInFormValidator): FormValidator<SignInFormState>
}
