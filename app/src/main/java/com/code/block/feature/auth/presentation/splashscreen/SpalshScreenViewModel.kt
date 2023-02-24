package com.code.block.feature.auth.presentation.splashscreen // ktlint-disable filename

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.core.domain.resource.Resource
import com.code.block.core.presentation.components.Screen
import com.code.block.core.usecase.GetOwnUserIdUseCase
import com.code.block.core.util.ui.UiEvent
import com.code.block.usecase.auth.AuthenticateUseCase
import com.onesignal.OneSignal
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase,
    getOwnUserIdUseCase: GetOwnUserIdUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        OneSignal.setExternalUserId(getOwnUserIdUseCase())
        viewModelScope.launch {
            when (authenticateUseCase()) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.HomeScreen.route)
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.Navigate(Screen.LoginScreen.route)
                    )
                }
            }
        }
    }
}
