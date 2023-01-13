package com.code.block.feature.auth.presentation.splashscreen // ktlint-disable filename

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.core.utils.Resource
import com.code.block.core.utils.UiEvent
import com.code.block.feature.auth.domain.usecase.AuthenticateUseCase
import com.code.block.feature.destinations.HomeScreenDestination
import com.code.block.feature.destinations.LoginScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authenticateUseCase: AuthenticateUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            when (authenticateUseCase()) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.Navigate(HomeScreenDestination.route)
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.Navigate(LoginScreenDestination.route)
                    )
                }
            }
        }
    }
}
