package com.code.block.feature.auth.domain.repository

import com.code.block.core.utils.SimpleResource

interface AuthRepository {

    suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource

    suspend fun login(
        email: String,
        password: String
    ): SimpleResource
}
