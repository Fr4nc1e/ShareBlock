package com.code.block.presentation.loginscreen

data class LoginState(
    val usernameText: String = "",
    val passwordText: String = "",
    val showPassword: Boolean = false,
    val usernameError: String = "",
    val passwordError: String = ""
)
