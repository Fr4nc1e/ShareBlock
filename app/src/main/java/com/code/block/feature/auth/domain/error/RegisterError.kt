package com.code.block.feature.auth.domain.error

data class RegisterError(
    val emailError: AuthError? = null,
    val usernameError: AuthError? = null,
    val passwordError: AuthError? = null,
)
