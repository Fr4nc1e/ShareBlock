package com.code.block.feature.auth.domain.model

import com.code.block.core.domain.util.RegisterResource
import com.code.block.feature.auth.domain.error.RegisterError

data class RegisterResult(
    val registerError: RegisterError? = null,
    val result: RegisterResource? = null
)
