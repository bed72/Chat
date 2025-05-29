package com.bed.chat.presentation.shared.viewmodel

import javax.inject.Inject

import androidx.lifecycle.ViewModel

import kotlinx.coroutines.flow.SharedFlow

import dagger.hilt.android.lifecycle.HiltViewModel

import com.bed.chat.domain.repositories.SessionRepository

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val repository: SessionRepository
) : ViewModel() {

     val state:  SharedFlow<Unit> = repository.sessionExpiredFlow

    suspend fun logout() {
        repository.logout()
    }
}
