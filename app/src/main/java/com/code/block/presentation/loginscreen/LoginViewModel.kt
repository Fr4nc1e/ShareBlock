package com.code.block.presentation.loginscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(LoginState())

    fun setUsernameText(username: String) {
        state = state.copy(usernameText = username)
    }

    fun setPasswordText(password: String) {
        state = state.copy(passwordText = password)
    }

    fun setIsUsernameError(error: String) {
        state = state.copy(usernameError = error)
    }

    fun setIsPasswordError(error: String) {
        state = state.copy(passwordError = error)
    }

    fun setShowPassword(showPassword: Boolean) {
        state = state.copy(showPassword = showPassword)
    }
}
