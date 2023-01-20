package com.code.block.feature.profile.presentation.profilescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.core.domain.usecase.GetOwnUserIdUseCase
import com.code.block.core.utils.Resource
import com.code.block.core.utils.UiEvent
import com.code.block.core.utils.UiText
import com.code.block.feature.profile.domain.usecase.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("userId")?.let {
            getProfile(it)
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.GetProfile -> {
                getProfile(userId = event.userId)
            }
        }
    }

    fun getProfile(userId: String?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            profileUseCases.getProfileUseCase(
                userId ?: getOwnUserIdUseCase()
            ).apply {
                when (this) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            profile = this.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(isLoading = false)
                        _eventFlow.emit(
                            UiEvent.SnackBarEvent(this.uiText ?: UiText.unknownError())
                        )
                    }
                }
            }
        }
    }
}
