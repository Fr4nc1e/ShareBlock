package com.code.block.usecase.auth

import com.code.block.core.util.Validation
import com.code.block.feature.auth.domain.model.RegisterResult
import com.code.block.feature.auth.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): RegisterResult {
        Validation.checkRegister(
            email = email,
            username = username,
            password = password
        ).also {
            if (it.emailError != null ||
                it.usernameError != null ||
                it.passwordError != null
            ) return RegisterResult(
                registerError = it
            )
        }

        val result = repository.register(
            email.trim(),
            username.trim(),
            password.trim()
        )

        return RegisterResult(result = result)
    }
}
