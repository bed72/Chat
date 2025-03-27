package com.bed.chat.presentation.shared.extensions

import kotlinx.coroutines.Job

import kotlinx.coroutines.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

fun ViewModel.launch(action: suspend () -> Unit): Job =
    viewModelScope.launch { action() }
