package com.code.block.feature.auth.domain.repository

import com.code.block.core.utils.AuthenticationResource
import com.code.block.core.utils.LoginResource
import com.code.block.core.utils.RegisterResource

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
