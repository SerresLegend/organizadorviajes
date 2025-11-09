package com.tuapp.organizadorviajes.presentation.screens.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }
}