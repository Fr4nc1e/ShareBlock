package com.code.block.feature.auth.domain.usecase

import com.code.block.core.domain.util.AuthenticationResource
import com.code.block.feature.auth.domain.repository.AuthRepository

class AuthenticateUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): AuthenticationResource {
        return repository.authenticate()
    }
}
