package com.code.block.feature.profile.presentation.followinfoscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.core.domain.resource.Resource
import com.code.block.core.usecase.GetOwnUserIdUseCase
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.UiText
import com.code.block.feature.profile.presentation.followinfoscreen.type.FollowType
import com.code.block.usecase.profile.ProfileUseCases
import com.code.block.usecase.profile.components.FollowUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowInfoViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val followUserUseCase: FollowUserUseCase,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(FollowInfoState())
    val state: State<FollowInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _ownUserId = mutableStateOf("")
    val ownUserId: State<String> = _ownUserId

    init {
        savedStateHandle.get<String>("followType")?.let { followType ->
            when (followType) {
                FollowType.Followers.type -> {
                    savedStateHandle.get<String>("userId")?.let {
                        getFollowers(it)
                        _ownUserId.value = getOwnUserIdUseCase()
                    }
                }
                FollowType.Followings.type -> {
                    savedStateHandle.get<String>("userId")?.let {
                        getFollowings(it)
                        _ownUserId.value = getOwnUserIdUseCase()
                    }
                }
                else -> Unit
            }
        }
    }

    fun onEvent(event: FollowInfoEvent) {
        when (event) {
            is FollowInfoEvent.FollowUser -> {
                followUser(event.userId)
            }
        }
    }

    private fun getFollowers(userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )
            profileUseCases.getFollowersUseCase(userId).apply {
                when (this) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UiEvent.SnackBarEvent(
                                uiText = this.uiText ?: UiText.unknownError()
                            )
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            users = this.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun getFollowings(userId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )
            profileUseCases.getFollowingsUseCase(userId).apply {
                when (this) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UiEvent.SnackBarEvent(
                                uiText = this.uiText ?: UiText.unknownError()
                            )
                        )
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            users = this.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun followUser(userId: String) {
        viewModelScope.launch {
            val isFollowing = state.value.users.find {
                it.userId == userId
            }?.isFollowing == true

            _state.value = state.value.copy(
                users = state.value.users.map {
                    if (it.userId == userId) {
                        it.copy(isFollowing = !it.isFollowing)
                    } else it
                }
            )

            val result = followUserUseCase(
                userId = userId,
                isFollowing = isFollowing
            )
            when (result) {
                is Resource.Success -> Unit
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        users = state.value.users.map {
                            if (it.userId == userId) {
                                it.copy(isFollowing = isFollowing)
                            } else it
                        }
                    )
                    _eventFlow.emit(
                        UiEvent.SnackBarEvent(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }
}
