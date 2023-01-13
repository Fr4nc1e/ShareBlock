package com.code.block.feature.auth.domain.model

import com.code.block.core.utils.LoginResource
import com.code.block.feature.auth.domain.error.LoginError

data class LoginResult(
    val loginError: LoginError? = null,
    val result: LoginResource? = null
)
