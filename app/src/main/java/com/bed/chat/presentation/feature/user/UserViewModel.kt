package com.bed.chat.presentation.feature.user

import javax.inject.Inject

import androidx.paging.cachedIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.chat.domain.repositories.UserRepository

@HiltViewModel
class UserViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {
    val users= userRepository().cachedIn(viewModelScope)
}
