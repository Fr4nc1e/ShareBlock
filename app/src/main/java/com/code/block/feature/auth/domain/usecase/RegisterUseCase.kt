package com.code.block.feature.auth.domain.usecase

import com.code.block.core.utils.SimpleResource
import com.code.block.feature.auth.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): SimpleResource {
        return repository.register(
            email = email.trim(),
            username = username.trim(),
            password = password.trim()
        )
    }
}
