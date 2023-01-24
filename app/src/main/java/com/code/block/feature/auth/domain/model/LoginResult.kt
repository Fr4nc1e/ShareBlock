package com.code.block.feature.auth.domain.model

import com.code.block.core.domain.resource.LoginResource
import com.code.block.feature.auth.domain.error.LoginError

data class LoginResult(
    val loginError: LoginError? = null,
    val result: LoginResource? = null
)
