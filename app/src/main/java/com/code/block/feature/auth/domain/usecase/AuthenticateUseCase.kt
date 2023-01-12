package com.code.block.feature.auth.domain.usecase

import com.code.block.core.utils.SimpleResource
import com.code.block.feature.auth.domain.repository.AuthRepository

class AuthenticateUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): SimpleResource {
        return repository.authenticate()
    }
}
