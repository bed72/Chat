package com.bed.chat.presentation.feature.users

import javax.inject.Inject

import androidx.paging.cachedIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.chat.domain.repositories.UsersRepository

@HiltViewModel
class UsersViewModel @Inject constructor(
    repository: UsersRepository
) : ViewModel() {
    val users= repository.getUsers().cachedIn(viewModelScope)
}
