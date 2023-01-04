package com.code.block.presentation.loginscreen

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.code.block.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _state.value = _state.value.copy(
                    emailText = event.email
                )
            }
            is LoginEvent.EnteredPassword -> {
                _state.value = _state.value.copy(
                    passwordText = event.password
                )
            }
            is LoginEvent.ClearEmail -> {
                _state.value = _state.value.copy(
                    emailText = ""
                )
            }
            is LoginEvent.TogglePasswordVisibility -> {
                _state.value = _state.value.copy(
                    isPasswordVisible = !state.value.isPasswordVisible
                )
            }
            is LoginEvent.Login -> {
                checkEmailAndPassword(
                    email = state.value.emailText,
                    password = state.value.passwordText
                )
            }
        }
    }

    private fun checkEmailAndPassword(email: String, password: String) {
        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()

        if (trimmedEmail.isBlank()) {
            _state.value = _state.value.copy(
                emailError = LoginState.EmailError.FieldEmpty
            )
            return
        }
        if (trimmedPassword.isBlank()) {
            _state.value = _state.value.copy(
                passwordError = LoginState.PasswordError.FieldEmpty
            )
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.value = _state.value.copy(
                emailError = LoginState.EmailError.InvalidEmail
            )
            return
        }
        if (trimmedPassword != Constants.TEST_PASSWORD || trimmedEmail != Constants.TEST_EMAIL) {
            _state.value = _state.value.copy(
                authError = true,
                passwordError = LoginState.PasswordError.InvalidPassword
            )
            return
        }

        _state.value = _state.value.copy(
            emailError = null,
            passwordError = null,
            authError = false
        )
    }
}
