package com.code.block.feature.auth.domain.model

import com.code.block.core.utils.SimpleResource
import com.code.block.feature.auth.presentation.util.AuthError

data class LoginResult(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)
