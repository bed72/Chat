package com.bed.chat.presentation.shared.extensions

import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

fun ViewModel.launch(action: suspend () -> Unit) {
    viewModelScope.launch { action() }
}
