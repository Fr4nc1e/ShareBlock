package com.code.block.core.util

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    data class SnackBarEvent(val uiText: UiText) : UiEvent()
    object NavigateUp : UiEvent()
    object OnLogin : UiEvent()
}
