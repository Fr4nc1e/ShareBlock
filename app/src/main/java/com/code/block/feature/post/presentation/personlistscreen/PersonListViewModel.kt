package com.code.block.feature.post.presentation.personlistscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.block.core.domain.resource.Resource
import com.code.block.core.usecase.GetOwnUserIdUseCase
import com.code.block.core.util.ui.UiEvent
import com.code.block.core.util.ui.UiText
import com.code.block.usecase.post.PostUseCases
import com.code.block.usecase.profile.components.FollowUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val followUserUseCase: FollowUserUseCase,
    private val getOwnUserIdUseCase: GetOwnUserIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(PersonListState())
    val state: State<PersonListState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _ownUserId = mutableStateOf("")
    val ownUserId: State<String> = _ownUserId

    init {
        savedStateHandle.get<String>("parentId")?.let {
            getLikedUsers(parentId = it)
            _ownUserId.value = getOwnUserIdUseCase()
        }
    }

    fun onEvent(event: PersonListEvent) {
        when (event) {
            is PersonListEvent.FollowUser -> {
                followUser(event.userId)
            }
        }
    }

    private fun getLikedUsers(parentId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )
            postUseCases.getLikedUsersForParent(parentId).apply {
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
