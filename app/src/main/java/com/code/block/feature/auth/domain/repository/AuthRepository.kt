package com.code.block.feature.auth.domain.repository

import com.code.block.core.domain.resource.AuthenticationResource
import com.code.block.core.domain.resource.LoginResource
import com.code.block.core.domain.resource.RegisterResource

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
