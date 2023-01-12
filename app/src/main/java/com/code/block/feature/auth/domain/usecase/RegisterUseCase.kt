package com.code.block.feature.auth.domain.usecase

import com.code.block.core.utils.Validation
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
        val usernameError = Validation.validateUsername(username)
        val emailError = Validation.validateEmail(email)
        val passwordError = Validation.validatePassword(password)

        if (emailError != null ||
            usernameError != null ||
            passwordError != null
        ) return RegisterResult(
            emailError,
            usernameError,
            passwordError
        )

        val result = repository.register(
            email.trim(),
            username.trim(),
            password.trim()
        )

        return RegisterResult(result = result)
    }
}
