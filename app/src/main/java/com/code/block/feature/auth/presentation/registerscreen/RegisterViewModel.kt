package com.code.block.feature.auth.presentation.registerscreen

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.R
import com.code.block.core.domain.state.PasswordTextFieldState
import com.code.block.core.domain.state.TextFieldState
import com.code.block.core.utils.Constants
import com.code.block.core.utils.Resource
import com.code.block.core.utils.UiText
import com.code.block.feature.auth.domain.usecase.RegisterUseCase
import com.code.block.feature.auth.presentation.util.AuthError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _usernameState = mutableStateOf(TextFieldState())
    val usernameState: State<TextFieldState> = _usernameState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState

    private val _registerState = mutableStateOf(RegisterState())
    val registerState: State<RegisterState> = _registerState

    private val _snackBarEventFlow = MutableSharedFlow<UiEvent>()
    val snackBarEventFlow = _snackBarEventFlow.asSharedFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EnteredUsername -> {
                _usernameState.value = _usernameState.value.copy(
                    text = event.username
                )
            }
            is RegisterEvent.EnteredEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.email
                )
            }
            is RegisterEvent.EnteredPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.password
                )
            }
            is RegisterEvent.ClearUsername -> {
                _usernameState.value = _usernameState.value.copy(
                    text = ""
                )
            }
            is RegisterEvent.ClearEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = ""
                )
            }
            is RegisterEvent.TogglePasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }
            is RegisterEvent.Register -> {
                validateUsername(username = usernameState.value.text)
                validateEmail(email = emailState.value.text)
                validatePassword(password = passwordState.value.text)
                registerIfNoError()
            }
        }
    }

    private fun registerIfNoError() {
        if (emailState.value.error != null ||
            usernameState.value.error != null ||
            passwordState.value.error != null
        ) return

        viewModelScope.launch {
            _registerState.value = _registerState.value
                .copy(isLoading = true)
            val result = registerUseCase(
                email = emailState.value.text,
                username = usernameState.value.text,
                password = passwordState.value.text
            )

            when (result) {
                is Resource.Success -> {
                    _snackBarEventFlow.emit(
                        UiEvent.SnackBarEvent(
                            uiText = UiText.StringResource(R.string.register_successfully)
                        )
                    )
                    _registerState.value = _registerState.value
                        .copy(isLoading = false)
                }
                is Resource.Error -> {
                    _snackBarEventFlow.emit(
                        UiEvent.SnackBarEvent(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                    _registerState.value = _registerState.value
                        .copy(isLoading = false)
                }
            }
        }
    }

    private fun validateUsername(username: String) {
        val trimmedUsername = username.trim()
        if (trimmedUsername.isBlank()) {
            _usernameState.value = _usernameState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if (trimmedUsername.length < Constants.MIN_USERNAME_LENGTH) {
            _usernameState.value = _usernameState.value.copy(
                error = AuthError.InputTooShort
            )
            return
        }

        _usernameState.value = _usernameState.value.copy(
            error = null
        )
    }

    private fun validateEmail(email: String) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank()) {
            _emailState.value = _emailState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailState.value = _emailState.value.copy(
                error = AuthError.InvalidEmail
            )
            return
        }

        _emailState.value = _emailState.value.copy(
            error = null
        )
    }

    private fun validatePassword(password: String) {
        val trimmedPassword = password.trim()
        if (trimmedPassword.isBlank()) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.FieldEmpty
            )
            return
        }
        if (trimmedPassword.length < Constants.MIN_PASSWORD_LENGTH) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InputTooShort
            )
            return
        }
        val isCapitalLettersInPassword = password.any { it.isUpperCase() }
        val isNumbersInPassword = password.any { it.isDigit() }
        if (!isCapitalLettersInPassword || !isNumbersInPassword) {
            _passwordState.value = _passwordState.value.copy(
                error = AuthError.InvalidPassword
            )
            return
        }

        _passwordState.value = _passwordState.value.copy(
            error = null
        )
    }
}
