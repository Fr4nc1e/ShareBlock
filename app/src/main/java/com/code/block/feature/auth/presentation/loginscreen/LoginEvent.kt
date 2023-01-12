package com.code.block.feature.auth.presentation.loginscreen

import com.code.block.core.utils.UiText

sealed class LoginEvent {
    data class EnteredEmail(val email: String) : LoginEvent()
    data class EnteredPassword(val password: String) : LoginEvent()
    object ClearEmail : LoginEvent()
    object TogglePasswordVisibility : LoginEvent()
    object Login : LoginEvent()
}

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    data class SnackBarEvent(val uiText: UiText) : UiEvent()
}
