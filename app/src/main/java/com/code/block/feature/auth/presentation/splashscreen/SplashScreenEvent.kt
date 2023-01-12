package com.code.block.feature.auth.presentation.splashscreen

sealed class SplashScreenEvent

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
}
