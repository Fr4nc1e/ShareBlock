package com.code.block.feature.auth.domain.error

import com.code.block.core.domain.util.Error

sealed class AuthError : Error() {
    object FieldEmpty : AuthError()
    object InputTooShort : AuthError()
    object InvalidEmail : AuthError()
    object InvalidPassword : AuthError()
}
