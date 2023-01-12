package com.code.block.core.utils

import android.util.Patterns
import com.code.block.feature.auth.presentation.util.AuthError

object Validation {

    fun validateEmail(email: String): AuthError? {
        val trimmedEmail = email.trim()

        if (trimmedEmail.isBlank()) {
            return AuthError.FieldEmpty
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return AuthError.InvalidEmail
        }

        return null
    }

    fun validateUsername(username: String): AuthError? {
        val trimmedUsername = username.trim()

        if (trimmedUsername.isBlank()) {
            return AuthError.FieldEmpty
        }
        if (trimmedUsername.length < Constants.MIN_USERNAME_LENGTH) {
            return AuthError.InputTooShort
        }

        return null
    }

    fun validatePassword(password: String): AuthError? {
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
