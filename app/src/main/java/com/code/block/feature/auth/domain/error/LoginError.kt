package com.code.block.feature.auth.domain.error

data class LoginError(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
)
