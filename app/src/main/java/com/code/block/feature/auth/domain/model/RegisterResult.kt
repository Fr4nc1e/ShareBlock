package com.code.block.feature.auth.domain.model

import com.code.block.core.utils.SimpleResource
import com.code.block.feature.auth.presentation.util.AuthError

data class RegisterResult(
    val emailError: AuthError? = null,
    val usernameError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)
