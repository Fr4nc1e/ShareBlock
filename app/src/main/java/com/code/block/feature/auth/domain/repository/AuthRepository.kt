package com.code.block.feature.auth.domain.repository

import com.code.block.core.domain.util.AuthenticationResource
import com.code.block.core.domain.util.LoginResource
import com.code.block.core.domain.util.RegisterResource

interface AuthRepository {

    suspend fun register(
        email: String,
        username: String,
        password: String
    ): RegisterResource

    suspend fun login(
        email: String,
        password: String
    ): LoginResource

    suspend fun authenticate(): AuthenticationResource
}
