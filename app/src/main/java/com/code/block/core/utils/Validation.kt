package com.code.block.core.utils

import android.util.Patterns
import com.code.block.feature.auth.domain.error.AuthError
import com.code.block.feature.auth.domain.error.LoginError
import com.code.block.feature.auth.domain.error.RegisterError

object Validation {

    fun checkRegister(
        email: String,
        username: String,
        password: String
    ): RegisterError {
        val usernameError = validateUsername(username)
        val emailError = validateEmail(email)
        val passwordError = validatePassword(password)

        return RegisterError(
            emailError = emailError,
            usernameError = usernameError,
            passwordError = passwordError
        )
    }

    fun checkLogin(
        email: String,
        password: String
    ): LoginError {
        val emailError = validateEmail(email)
        val passwordError = validatePassword(password)

        return LoginError(
            emailError = emailError,
            passwordError = passwordError
        )
    }

    private fun validateEmail(email: String): AuthError? {
        val trimmedEmail = email.trim()

        if (trimmedEmail.isBlank()) {
            return AuthError.FieldEmpty
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return AuthError.InvalidEmail
        }

        return null
    }

    private fun validateUsername(username: String): AuthError? {
        val trimmedUsername = username.trim()

        if (trimmedUsername.isBlank()) {
            return AuthError.FieldEmpty
        }
        if (trimmedUsername.length < Constants.MIN_USERNAME_LENGTH) {
            return AuthError.InputTooShort
        }

        return null
    }

    private fun validatePassword(password: String): AuthError? {
        val trimmedPassword = password.trim()
        if (trimmedPassword.isBlank()) {
            return AuthError.FieldEmpty
        }
        if (trimmedPassword.length < Constants.MIN_PASSWORD_LENGTH) {
            return AuthError.InputTooShort
        }
        val isCapitalLettersInPassword = password.any { it.isUpperCase() }
        val isNumbersInPassword = password.any { it.isDigit() }
        if (!isCapitalLettersInPassword || !isNumbersInPassword) {
            return AuthError.InvalidPassword
        }

        return null
    }
}
