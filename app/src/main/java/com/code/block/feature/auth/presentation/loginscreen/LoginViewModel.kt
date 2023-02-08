package com.code.block.feature.auth.presentation.loginscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.core.domain.resource.Resource
import com.code.block.core.domain.state.PasswordTextFieldState
import com.code.block.core.domain.state.TextFieldState
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.UiText
import com.code.block.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _emailState.value = emailState.value.copy(
                    text = event.email,
                )
            }
            is LoginEvent.EnteredPassword -> {
                _passwordState.value = passwordState.value.copy(
                    text = event.password,
                )
            }
            is LoginEvent.ClearEmail -> {
                _emailState.value = TextFieldState()
            }
            is LoginEvent.TogglePasswordVisibility -> {
                _passwordState.value = passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible,
                )
            }
            is LoginEvent.Login -> {
                login()
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _loginState.value = loginState.value.copy(isLoading = true)

            loginUseCase(
                email = emailState.value.text,
                password = passwordState.value.text,
            ).also { loginResult ->
                loginResult.loginError?.let {
                    it.emailError?.let {
                        _emailState.value = emailState.value.copy(
                            error = loginResult.loginError.emailError,
                        )
                    }
                    it.passwordError?.let {
                        _passwordState.value = _passwordState.value.copy(
                            error = loginResult.loginError.passwordError,
                        )
                    }
                }
                when (loginResult.result) {
                    is Resource.Success -> {
                        _eventFlow.emit(UiEvent.OnLogin)
                        initial()
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(
                            UiEvent.SnackBarEvent(
                                loginResult.result.uiText ?: UiText.unknownError(),
                            ),
                        )
                    }
                    null -> {
                        _loginState.value = _loginState.value
                            .copy(isLoading = false)
                    }
                }
            }
        }
    }

    private fun initial() {
        _loginState.value = _loginState.value
            .copy(isLoading = false)
        _emailState.value = TextFieldState()
        _passwordState.value = PasswordTextFieldState()
    }
}
