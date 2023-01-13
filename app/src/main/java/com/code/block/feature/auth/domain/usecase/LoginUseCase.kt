package com.code.block.feature.auth.domain.usecase

import com.code.block.feature.auth.domain.model.LoginResult
import com.code.block.feature.auth.domain.repository.AuthRepository
import com.code.block.feature.auth.presentation.util.AuthError

class LoginUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): LoginResult {
        val emailError = if (email.isBlank()) {
            AuthError.FieldEmpty
        } else null

        val passwordError = if (password.isBlank()) {
            AuthError.FieldEmpty
        } else null

        if (emailError != null ||
            passwordError != null
        ) return LoginResult(
            emailError = emailError,
            passwordError = passwordError
        )

        return LoginResult(
            result = repository.login(
                email = email,
                password = password
            )
        )
    }
}
