package com.code.block.feature.profile.presentation.profilescreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private fun getProfile(userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )

            profileUseCases.getProfileUseCase(userId).apply {
                when (this) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UiEvent.SnackBarEvent(this.uiText ?: UiText.unknownError())
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            profile = this.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}
