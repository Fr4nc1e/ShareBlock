package com.code.block.usecase.auth

import com.code.block.core.domain.resource.AuthenticationResource
import com.code.block.feature.auth.domain.repository.AuthRepository

class AuthenticateUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): AuthenticationResource {
        return repository.authenticate()
    }
}
