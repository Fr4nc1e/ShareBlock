package com.code.block.core.util.ui

import com.code.block.core.domain.parent.Event

sealed class UiEvent : Event() {
    data class Navigate(val route: String) : UiEvent()
    data class SnackBarEvent(val uiText: UiText) : UiEvent()
    object NavigateUp : UiEvent()
    object OnLogin : UiEvent()
    object OnLikeParent : UiEvent()
}
