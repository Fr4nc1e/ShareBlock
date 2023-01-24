package com.code.block.core.util

import com.code.block.core.domain.util.Event

sealed class UiEvent : Event() {
    data class Navigate(val route: String) : UiEvent()
    data class SnackBarEvent(val uiText: UiText) : UiEvent()
    object NavigateUp : UiEvent()
    object OnLogin : UiEvent()
    object OnLikeParent : UiEvent()
}
