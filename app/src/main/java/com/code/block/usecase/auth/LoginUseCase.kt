package com.code.block.usecase.auth

import com.code.block.core.util.Validation
import com.code.block.feature.auth.domain.model.LoginResult
import com.code.block.feature.auth.domain.repository.AuthRepository

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): LoginResult {
        Validation.checkLogin(
            email = email,
            password = password
        ).also {
            if (it.emailError != null || it.passwordError != null) {
                return LoginResult(
                    loginError = it
                )
            }
        }

        return LoginResult(
            result = repository.login(
                email = email,
                password = password
            )
        )
    }
}
